package pet.perpet.domain.usecase

import pet.perpet.data.repository.base.Repository


open class UseCase2 {

    //======================================================================
    // Variables
    //======================================================================

    private val map: HashMap<String, Any> = hashMapOf()

    //======================================================================
    // Public Methods
    //======================================================================

    @Suppress("UNCHECKED_CAST")
    fun <Repo : Repository<*, RawResult>, Parameter, DomainResult, RawResult, Use : UseCase<Repo, Parameter, DomainResult, RawResult>> getRepositoryBehavior(
        clazz: Class<*>,
        observer: (obj: Use) -> Unit
    ): Use {
        return synchronized(map) {
            val key = getKey(clazz)
            val ins = map.getOrPut(key) {
                clazz.newInstance()
            } as Use

            observer.invoke(ins)
            ins
        }
    }

    fun onDestroy() {
        map.clear()
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun getKey(clazz: Class<*>): String {
        return "key:${clazz.name}"
    }
}

