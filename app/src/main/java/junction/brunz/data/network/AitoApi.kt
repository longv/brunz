package junction.brunz.data.network

import io.reactivex.Completable
import io.reactivex.Single
import junction.brunz.data.model.base.DeleteResponse
import junction.brunz.data.model.base.RecommendRequest
import junction.brunz.data.model.base.SearchRequest
import junction.brunz.data.model.place.PlacesResponse
import junction.brunz.data.model.session.SessionModel
import junction.brunz.data.model.session.SessionVoteModel
import junction.brunz.data.model.session.SessionVotesResponse
import junction.brunz.data.model.session.SessionsResponse
import junction.brunz.data.model.user.UserModel
import junction.brunz.data.model.user.UsersResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Long Vu on 6.11.2020
 */
interface AitoApi {

  @POST("api/v1/_query")
  fun getRecommendPlaces(@Body request: RecommendRequest): Single<PlacesResponse>

  @POST("api/v1/_search")
  fun getPlaces(@Body request: SearchRequest): Single<PlacesResponse>

  @POST("api/v1/_search")
  fun getUsers(@Body request: SearchRequest): Single<UsersResponse>

  @POST("api/v1/_search")
  fun getTeamSessions(@Body request: SearchRequest): Single<SessionsResponse>

  @POST("api/v1/data/sessions")
  fun createTeamSession(@Body model: SessionModel): Single<SessionModel>

  @POST("api/v1/_search")
  fun getTeamSessionVotes(@Body request: SearchRequest): Single<SessionVotesResponse>

  @POST("api/v1/data/sessionVote")
  fun createTeamSessionVote(@Body model: SessionVoteModel): Single<SessionVoteModel>

  @POST("api/v1/data/users")
  fun createUser(@Body model: UserModel): Single<UserModel>

  @POST("api/v1/data/_delete")
  fun delete(@Body request: SearchRequest): Single<DeleteResponse>

  companion object {

    private val instance = RetrofitProvider.provide()
      .create(AitoApi::class.java)

    fun get(): AitoApi = instance
  }
}