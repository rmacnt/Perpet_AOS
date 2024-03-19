package pet.perpet.data.api.request.account

import com.google.gson.annotations.SerializedName

data class MedicalHookRequest (
    @SerializedName("q_url")
    val q_url: String?,
    @SerializedName("response_id")
    val response_id: String = "R_1Qo6qs5PpQ3jlO0",
    @SerializedName("response_link")
    val response_link: String = "https://equal.hnd1.qualtrics.com/apps/single-response-reports/reports/WLWRRWRk7-5DkBwZw%2EP1UaAk2XmWVGYi0DnZLJtFdIVvgSZKkbHSvgLLShLVty-e6-E40KQfWvmpIYyo15J7nQVsgKqvDKkyYvKQal%2Epxcqvm1IS00ZyznGBUGTRIJc547pA7wnFb5gWoHwALLHujZTtNTqEPBimLHZBrZgUkP6kYvTB8MNChoJbtU0IVJ9b3iEB4lmB5B7oV2WEzHsNiCb%2ETcyvzq2qywkKh2qRL9J%2ENJt9-IfGVUD%2EbQAdAECz0kMdebWo2LKFWrxdX9sB1BYrFftP%2ELs4qcZ1AIn%2EEYIbz8uCgDmN0OGraMO65JbWTNJpQsXSZWetP0vQChaLfw"
)