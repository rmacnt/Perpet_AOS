package pet.perpet.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonLoader {

    val gson: Gson by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            /*.registerTypeAdapter(DailyGuide.class, new DailyGuideDeserializer())*/
            /*.registerTypeAdapter(UserAnalysis.class, new UserAnalysisDeserializer())*/
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create()
    }
}