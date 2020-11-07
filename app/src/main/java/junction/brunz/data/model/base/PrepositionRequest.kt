package junction.brunz.data.model.base

/**
 * Created by Long Vu on 7.11.2020
 */
sealed class PrepositionRequest : HashMap<String, Any>() {

  class CompositePrepositionRequest(
    vararg prepositions: PrepositionRequest
  ) : PrepositionRequest() {

    init {
      prepositions.forEach {
        putAll(it)
      }
    }
  }

  data class QueryPrepositionRequest(
    val key: String,
    val value: Any
  ) : PrepositionRequest() {

    init {
      put(key, value)
    }
  }

  data class AtomicOperatorRequest(
    val atomic: PrepositionRequest
  ) : PrepositionRequest() {

    init {
      put("\$atomic", atomic)
    }
  }

  data class MultiplyOperatorRequest(
    val multiply: List<PrepositionRequest>
  ) : PrepositionRequest() {

    init {
      put("\$multiply", multiply)
    }
  }

  data class ProbabilityOperatorRequest(
    val p: PrepositionRequest
  ) : PrepositionRequest() {

    init {
      put("\$p", p)
    }
  }

  data class ContextOperatorRequest(
    val context: PrepositionRequest
  ) : PrepositionRequest() {

    init {
      put("\$context", context)
    }
  }

  data class SimilarityOperatorRequest(
    val similarity: PrepositionRequest
  ) : PrepositionRequest() {

    init {
      put("\$similarity", similarity)
    }
  }

  data class AndOperatorRequest(
    val and: List<PrepositionRequest>
  ) : PrepositionRequest() {

    init {
      put("\$and", and)
    }
  }

  data class LteOperatorRequest(
    val lte: Double
  ) : PrepositionRequest() {

    init {
      put("\$lte", lte)
    }
  }

  data class GtOperatorRequest(
    val gt: Double
  ) : PrepositionRequest() {

    init {
      put("\$gt", gt)
    }
  }

  data class HasOperatorRequest(
    val has: String
  ) : PrepositionRequest() {

    init {
      put("\$has", has)
    }
  }
}