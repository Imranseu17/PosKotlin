package com.example.pos_kotlin


import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_desh_board.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DeshBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desh_board)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setTitle(" Home ")
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_sort_black_24dp)

       


        card_gas.setOnClickListener(View.OnClickListener {
            line_gas.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            line_account.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_inspect.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_refund.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_history.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))

            
        })

        card_account.setOnClickListener(View.OnClickListener {
            line_gas.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_account.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            line_inspect.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_refund.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_history.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))


        })

        card_inspect.setOnClickListener(View.OnClickListener {
            line_gas.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_account.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_inspect.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            line_refund.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_history.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))

        })

        card_refund.setOnClickListener(View.OnClickListener {
            line_gas.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_account.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_inspect.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_refund.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            line_history.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))


        })

        card_history.setOnClickListener(View.OnClickListener {
            line_gas.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_account.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_inspect.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_refund.setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
            line_history.setBackgroundColor(ContextCompat.getColor(this, R.color.green))



        })

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_page, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_setting -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed();
        showExitDialog()
    }

    fun showExitDialog() {
        ChooseAlertDialog(this)
            .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
            .setAnimationEnable(true)
            .setTitleText("Warning")
            .setContentText("exit")
            .setNegativeListener(getString(R.string.no), object : ChooseAlertDialog.OnNegativeListener {
                override fun onClick(dialog: ChooseAlertDialog) {
                    dialog.dismiss()
                }
            })
            .setPositiveListener(getString(R.string.yes), object : ChooseAlertDialog.OnPositiveListener {
                override fun onClick(dialog: ChooseAlertDialog) {
                    SharedDataSaveLoad.remove(this@DeshBoardActivity, getString(R.string.preference_is_service_check))
                    SharedDataSaveLoad.remove(this@DeshBoardActivity, getString(R.string.preference_access_token))
                    dialog.dismiss()
                    finish()

                }
            }).show()
    }




}
