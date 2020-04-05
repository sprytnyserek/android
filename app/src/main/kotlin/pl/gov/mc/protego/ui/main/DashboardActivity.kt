package pl.gov.mc.protego.ui.main

import android.os.Bundle
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.gov.mc.protego.R
import pl.gov.mc.protego.information.Session
import pl.gov.mc.protego.ui.base.BaseActivity

class DashboardActivity : BaseActivity() {

    private val viewModel: DashboardActivityViewModel by viewModel()
    private val session: Session by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}
