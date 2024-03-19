package pet.perpet.data.repository.base.datasource.exception

import java.io.IOException

class NetworkConnectionErrorException(message: String?) :
    IOException(message)