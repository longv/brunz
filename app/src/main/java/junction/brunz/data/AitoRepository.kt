package junction.brunz.data

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import junction.brunz.data.model.base.PrepositionRequest
import junction.brunz.data.model.base.RecommendRequest
import junction.brunz.data.model.base.SearchRequest
import junction.brunz.data.model.place.PlaceModel
import junction.brunz.data.model.session.SessionModel
import junction.brunz.data.model.session.SessionVoteModel
import junction.brunz.data.model.user.UserModel
import junction.brunz.data.network.AitoApi
import junction.brunz.presentation.profile.ProfileCreateFragment

/**
 * Created by Long Vu on 7.11.2020
 */
object AitoRepository {

  private lateinit var currentUser: UserModel

  private var sessions: List<SessionModel> = emptyList()

  private var teamMembers: List<UserModel> = emptyList()

  private var suggestedPlaces: List<PlaceModel> = emptyList()

  fun getTeamRecommendPlaces(): Single<List<PlaceModel>> {
    return getTeamMembers().flatMap { teamMembers ->
      val request = RecommendRequest(
        from = "ratings",
        get = "placeID",
        where = PrepositionRequest.QueryPrepositionRequest(
          "userID.groupID", currentUser.groupId
        ),
        orderBy = PrepositionRequest.MultiplyOperatorRequest(
          multiply = listOf(
            PrepositionRequest.ProbabilityOperatorRequest(
              p = PrepositionRequest.ContextOperatorRequest(
                context = PrepositionRequest.QueryPrepositionRequest(
                  "rating", PrepositionRequest.AtomicOperatorRequest(
                    atomic = PrepositionRequest.CompositePrepositionRequest(
                      PrepositionRequest.GtOperatorRequest(
                        gt = 0.5
                      ),
                      PrepositionRequest.LteOperatorRequest(
                        lte = 2.5
                      )
                    )
                  ))
              )
            ),
            PrepositionRequest.SimilarityOperatorRequest(
              similarity = PrepositionRequest.AtomicOperatorRequest(
                atomic = PrepositionRequest.CompositePrepositionRequest(
                  teamMembers.createHasCuisinePrepositions().createCuisinePreposition()
                )
              )
            )
          )
        ),
        limit = 5
      )

      AitoApi.get().getRecommendPlaces(request)
        .map { it.hits }
    }
  }

  fun getTeamSessionRecommendPlaces(suggestedPlaceIds: String): Single<List<PlaceModel>> {
    val request = SearchRequest(
      from = "places",
      where = PrepositionRequest.OrOperatorRequest(
        or = suggestedPlaceIds.split(";").filter { it.isNotEmpty() }.map {
          PrepositionRequest.QueryPrepositionRequest("placeID", it)
        }
      )
    )
    return AitoApi.get().getPlaces(request)
      .map { it.hits }
      .doOnSuccess { suggestedPlaces = it }
  }

  fun voteForTeamSession(sessionId: String, placeId: String): Completable {
    val newFavorite = currentUser.favorite?.plus(";$placeId")

    val placeCuisines = suggestedPlaces.first { it.placeId == placeId }
      .cuisine?.split(";")?.filter { it.isNotEmpty() && it != "International" } ?: emptyList()
    val userCuisines = currentUser.cuisine.split(";").filter { it.isNotEmpty() }
    val newCuisines = userCuisines.plus(placeCuisines).distinct().joinToString(";") { it.capitalize() }

    return updateUser(currentUser.copy(cuisine = newCuisines, favorite = newFavorite))
      .flatMap {
        val model = SessionVoteModel(
          sessionId = sessionId,
          userId = currentUser.userId,
          vote = placeId
        )
        AitoApi.get().createTeamSessionVote(model)
      }
      .ignoreElement()
  }

  fun getSessionResult(sessionId: String): Maybe<PlaceModel> {
    val request = SearchRequest(
      from = "sessionVote",
      where = PrepositionRequest.QueryPrepositionRequest("sessionID", sessionId)
    )
    return AitoApi.get().getTeamSessionVotes(request)
      .map { it.hits }
      .flatMapMaybe { votes ->
        val voteDone = votes.size >= teamMembers.size
        if (voteDone) {
          val chosenPlaceId = votes.groupBy { it.vote }
            .entries.maxBy { it.value.size }!!
            .key
          val session = sessions.first { it.sessionId == sessionId }.copy(chosenPlaceId = chosenPlaceId, isOpen = false)

          updateSession(session)
            .flatMapMaybe {
              Maybe.just(
                suggestedPlaces.first { it.placeId == chosenPlaceId }
              )
            }
        } else {
          Maybe.empty()
        }
      }
  }

  fun getPersonalRecommendPlaces(): Single<List<PlaceModel>> {
    return Single.defer {
      val request = RecommendRequest(
        from = "ratings",
        get = "placeID",
        orderBy = PrepositionRequest.MultiplyOperatorRequest(
          multiply = listOf(
            PrepositionRequest.ProbabilityOperatorRequest(
              p = PrepositionRequest.ContextOperatorRequest(
                context = PrepositionRequest.QueryPrepositionRequest(
                  "rating", PrepositionRequest.AtomicOperatorRequest(
                    atomic = PrepositionRequest.CompositePrepositionRequest(
                      PrepositionRequest.GtOperatorRequest(
                        gt = 0.5
                      ),
                      PrepositionRequest.LteOperatorRequest(
                        lte = 2.5
                      )
                    )
                  ))
              )
            ),
            PrepositionRequest.SimilarityOperatorRequest(
              similarity = PrepositionRequest.AtomicOperatorRequest(
                atomic = PrepositionRequest.CompositePrepositionRequest(
                  currentUser.createHasCuisinePrepositions().createCuisinePreposition()
                )
              )
            )
          )
        )
      )

      AitoApi.get().getRecommendPlaces(request)
        .map { it.hits }
    }
  }

  fun createUser(user: UserModel = UserModel()): Single<UserModel> {
    return AitoApi.get().createUser(user)
      .doOnSuccess {
        Log.d(ProfileCreateFragment::class.java.simpleName, "New user create: ${it.userId}")

        SessionPreference.userId = it.userId
        currentUser = it
      }
  }

  fun updateUser(user: UserModel): Single<UserModel> {
    val request = SearchRequest(
      from = "users",
      where = PrepositionRequest.QueryPrepositionRequest("userID", user.userId)
    )
    return AitoApi.get().delete(request)
      .ignoreElement()
      .andThen(createUser(user))
  }

  fun getUser(userId: String): Single<UserModel> {
    val request = SearchRequest(
      from = "users",
      where = PrepositionRequest.QueryPrepositionRequest("userID", userId)
    )
    return AitoApi.get().getUsers(request)
      .map { it.hits.firstOrNull() ?: error("User with id $userId not found!") }
      .doOnSuccess {
        Log.d(ProfileCreateFragment::class.java.simpleName, "Get user: ${it.userId}")
        currentUser = it
      }
      .doOnError {
        Log.d(ProfileCreateFragment::class.java.simpleName, "User with id $userId not found!")
      }
  }

  fun getTeamMembers(): Single<List<UserModel>> {
    val request = SearchRequest(
      from = "users",
      where = PrepositionRequest.QueryPrepositionRequest("groupID", currentUser.groupId)
    )
    return AitoApi.get().getUsers(request)
      .map { it.hits }
      .doOnSuccess { teamMembers = it }
  }

  fun getTeamSessions(): Single<List<SessionModel>> {
    val request = SearchRequest(
      from = "sessions",
      where = PrepositionRequest.QueryPrepositionRequest("teamID", currentUser.groupId)
    )
    return AitoApi.get().getTeamSessions(request)
      .map { it.hits }
      .flatMap { sessions ->
        val openingSession = sessions.find { it.isOpen }
        if (openingSession == null) {
          getTeamRecommendPlaces()
            .flatMap { places ->
              val sessionModel = SessionModel(
                groupId = currentUser.groupId.orEmpty(),
                isOpen = true,
                suggestedPlaceIds = places.joinToString(";") { it.placeId }
              )
              AitoApi.get().createTeamSession(sessionModel)
            }.map { newSession ->
              sessions.plus(newSession)
            }
        } else {
          Single.just(sessions)
        }
      }.map { sessions ->
        sessions.sortedByDescending { it.isOpen }
      }.doOnSuccess {
        sessions = it
      }
  }

  private fun updateSession(session: SessionModel): Single<SessionModel> {
    val request = SearchRequest(
      from = "sessions",
      where = PrepositionRequest.QueryPrepositionRequest("sessionID", session.sessionId)
    )
    return AitoApi.get().delete(request)
      .ignoreElement()
      .andThen(AitoApi.get().createTeamSession(session))
  }

  private fun List<PrepositionRequest>.createCuisinePreposition(): PrepositionRequest {
    return PrepositionRequest.QueryPrepositionRequest(
      "cuisine", PrepositionRequest.OrOperatorRequest(
        or = this
      )
    )
  }

  private fun UserModel.createHasCuisinePrepositions(): List<PrepositionRequest> {
    val cuisines = cuisine.split(";")
      .filter { it.isNotEmpty() }
      .takeIf { it.isNotEmpty() }
      ?: listOf("International")

    return cuisines.map {
      PrepositionRequest.HasOperatorRequest(
        has = it
      )
    }
  }

  private fun List<UserModel>.createHasCuisinePrepositions(): List<PrepositionRequest> {
    return flatMap { it.createHasCuisinePrepositions() }
  }
}