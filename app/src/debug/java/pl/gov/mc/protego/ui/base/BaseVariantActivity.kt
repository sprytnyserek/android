package pl.gov.mc.protego.ui.base

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polidea.cockpit.cockpit.Cockpit
import com.polidea.cockpit.event.ActionRequestCallback
import org.koin.android.ext.android.inject
import org.koin.core.context.GlobalContext
import pl.gov.mc.protego.information.Session
import pl.gov.mc.protego.information.SessionState
import pl.gov.mc.protego.ui.registration.onboarding.OnboardingActivity

abstract class BaseVariantActivity : AppCompatActivity() {
    private val shakeDetector: CockpitShakeDetector by inject()
    private val logoutCallback: ActionRequestCallback = ActionRequestCallback {
        val session: Session = GlobalContext().get().get()
        if (session.sessionData.state == SessionState.LOGGED_IN) {
            session.logout()
            startActivity(Intent(this, OnboardingActivity::class.java))
//            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initShakeDetection()
    }

    override fun onStart() {
        super.onStart()
        initCockpit()
    }

    override fun onStop() {
        super.onStop()
        shakeDetector.stopDetection()
        deinitCockpit()
    }

    private fun initCockpit() {
        Cockpit.addLogoutActionRequestCallback(this, logoutCallback)
    }

    private fun deinitCockpit() {
        Cockpit.removeLogoutActionRequestCallback(logoutCallback)
    }

    private fun initShakeDetection() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        shakeDetector.startDetection(sensorManager)
        shakeDetector.setListener { CockpitMenuLauncher.showCockpit(supportFragmentManager) }
    }
}