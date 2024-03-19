package pet.perpet.domain.usecase.profile

import pet.perpet.data.api.request.pet.PetInsertRequest
import pet.perpet.domain.usecase.UseCase2
import pet.perpet.domain.usecase.pet.PetInsertUseCase

class SignUseCase : UseCase2() {

    fun getBreedList(search: String , type: String ): BreedListUseCase {
        return getRepositoryBehavior(BreedListUseCase::class.java) {
            it.parameter2 {
                this.search = search
                this.type = type

            }
        }
    }

    fun setBreed(name: String , type: String , userId: Int ): BreedSetUseCase {
        return getRepositoryBehavior(BreedSetUseCase::class.java) {
            it.parameter2 {
                this.name = name
                this.type = type
                this.userId = userId
            }
        }
    }

    fun setAllergy(name: String , userId: Int ): AllergySetUseCase {
        return getRepositoryBehavior(AllergySetUseCase::class.java) {
            it.parameter2 {
                this.name = name
                this.userId = userId
            }
        }
    }

    fun getDiseaseList(isMain : Boolean ): DiseaseListUseCase {
        return getRepositoryBehavior(DiseaseListUseCase::class.java) {
          it.parameter2 {
              this.isMain = isMain
          }
        }
    }

    fun getDiseaseCommentList(isMain : Boolean , name: String, mainCtgrId: Int ): DiseaseCommentListUseCase {
        return getRepositoryBehavior(DiseaseCommentListUseCase::class.java) {
            it.parameter2 {
                this.isMain = isMain
                this.name = name
                this.mainCtgrId = mainCtgrId
            }
        }
    }

    fun getAllergyList(name: String): AllergyListUseCase {
        return getRepositoryBehavior(AllergyListUseCase::class.java) {
            it.parameter2 {
                this.name = name
            }
        }
    }

    fun petInsert(petInsertRequest: PetInsertRequest): PetInsertUseCase {
        return getRepositoryBehavior(PetInsertUseCase::class.java) {
            it.parameter2 {
                this.petInsertRequest = petInsertRequest
            }
        }
    }
}