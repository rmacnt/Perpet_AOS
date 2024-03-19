package pet.perpet.data.repository.base.datasource.exception

import java.io.IOException

class ClientInternalErrorException(message: String?) :
    IOException(message)