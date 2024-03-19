package pet.perpet.framework.widget.webview.url

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import java.net.URISyntaxException

class DanalPayLoader : UrlLoader {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url != null && url != "about:blank") {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                if (url.contains("http://market.android.com") ||
                    url.contains("http://m.ahnlab.com/kr/site/download") ||
                    url.endsWith(".apk")
                ) {
                    return urlSchemeProc(view, url)
                } else {
                    return false
                }
            } else if (url.startsWith("mailto:")) {
                return false
            } else if (url.startsWith("tel:")) {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                view?.context?.startActivity(intent)
                return true
            } else {
                return urlSchemeProc(view, url)
            }
        }
        return true
    }

    override fun isUrlMatch(url: String): Boolean {
        return true
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun urlSchemeProc(view: WebView?, url: String?): Boolean {
        if (url != null && (url.contains("cloudpay")
                    || url.contains("hanaansim")
                    || url.contains("citispayapp")
                    || url.contains("citicardapp")
                    || url.contains("mvaccine")
                    || url.contains("com.TouchEn.mVaccine.webs")
                    || url.contains("market://")
                    || url.contains("smartpay")
                    || url.contains("com.ahnlab.v3mobileplus")
                    || url.contains("droidxantivirus")
                    || url.contains("v3mobile")
                    || url.endsWith(".apk")
                    || url.contains("market://")
                    || url.contains("ansimclick")
                    || url.contains("market://details?id=com.shcard.smartpay")
                    || url.contains("shinhan-sr-ansimclick://")
                    || url.contains("http://m.ahnlab.com/kr/site/download")
                    || url.contains("com.lotte.lottesmartpay")
                    || url.startsWith("lottesmartpay://")
                    || url.contains("http://market.android.com")
                    || url.contains("vguard")
                    || url.contains("smhyundaiansimclick://")
                    || url.contains("smshinhanansimclick://")
                    || url.contains("smshinhancardusim://")
                    || url.contains("smartwall://")
                    || url.contains("appfree://")
                    || url.startsWith("kb-acp://")
                    || url.startsWith("intent://")
                    || url.startsWith("ahnlabv3mobileplus")

                    || url.contains("smhyundaiansimclick")
                    || url.contains("smshinhanansimclick")
                    || url.contains("shinhan-sr-ansimclick")
                    || url.contains("vguardstart")
                    || url.contains("vguardend")
                    || url.contains("droidx3host")
                    || url.contains("mpocket.online.ansimclick")
                    || url.contains("hdcardappcardansimclick")
                    || url.contains("nhappcardansimclick")
                    || url.contains("nonghyupcardansimclick")
                    || url.contains("tswansimclick")
                    || url.contains("payco")
                    || url.contains("samsungpay")

                    //2017.07.31 하나 은행 추가 내용,
                    //하나카드 요청 내용
                    //① 안드로이드 : "bhcs://cardpotal.hanaskcard.com"
                    //② 마켓 연결 및 패키지명 : market://details?id=com.hanaskcard.rocomo.potal
                    || url.contains("hhcs") || url.contains("bhcs"))
        ) {
            try {
                var intent: Intent? = null
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                } catch (ex: URISyntaxException) {
                    return false
                }

                //chrome 버젼 방식 : 2014.01 추가
                if (url.startsWith("intent")) { // chrome 버젼 방식
                    // 앱설치 체크를 합니다.
                    if (view?.context?.packageManager?.resolveActivity(intent, 0) == null) {
                        val packagename = intent.getPackage()
                        if (packagename != null) {
                            val uri = Uri.parse("market://search?q=pname:$packagename")
                            intent = Intent(Intent.ACTION_VIEW, uri)
                            view?.startActivity(intent)
                            return true
                        }
                    }

                    val uri = Uri.parse(intent.dataString)
                    intent = Intent(Intent.ACTION_VIEW, uri)
                    view?.startActivity(intent)

                } else { // 구 방식
                    // 2017-08-04 하나카드 추가 내용 적용
                    if (url.startsWith("bhcs")) {
                        val packageName = "com.hanaskcard.rocomo.potal"
                        val appUrl = "bhcs://cardpotal.hanaskcard.com"
                        val marKet = "market://details?id=com.hanaskcard.rocomo.potal"

                        val pm = view?.context?.packageManager
                        var info: PackageInfo?
                        try {
                            info = pm?.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                        } catch (e: Exception) {
                            return false
                        }

                        try {
                            if (info != null) {
                                val uri: Uri
                                if (url.contains(appUrl)) {
                                    uri = Uri.parse(url)
                                } else {
                                    uri = Uri.parse(appUrl)
                                }

                                val hanaIntent = Intent(Intent.ACTION_VIEW, uri)
                                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                                view?.startActivity(hanaIntent)
                            } else {
                                val uri = Uri.parse(marKet)
                                val hanaIntent = Intent(Intent.ACTION_VIEW, uri)
                                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                                view?.startActivity(hanaIntent)
                            }
                        } catch (e: Exception) {
                            return false
                        }

                    } else {
                        val uri = Uri.parse(url)
                        intent = Intent(Intent.ACTION_VIEW, uri)
                        view?.startActivity(intent)
                    }
                }
            } catch (e: ActivityNotFoundException) {
                Log.e("error ===>", e.message.orEmpty())
                e.printStackTrace()
                return url.startsWith("vguardend://")
            }

        } else {
            view?.loadUrl(url.orEmpty())
            return false
        }
        return true
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun WebView.startActivity(intent: Intent) {
        this.context?.startActivity(intent)
    }
}