package com.example.moneytracker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moneytracker.databinding.ActivityMainBinding
import com.example.moneytracker.fragments.AccountsFragment
import com.example.moneytracker.fragments.AnalysisFragment
import com.example.moneytracker.fragments.BudgetFragment
import com.example.moneytracker.fragments.CategoriesFragment
import com.example.moneytracker.fragments.RecordsFragment
import com.example.moneytracker.fragments.TitleFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val titleFragment = TitleFragment()

    private val recordsFragment = RecordsFragment()
    private val analysisFragment = AnalysisFragment()
    private val budgetFragment = BudgetFragment()
    private val accountsFragment = AccountsFragment()
    private val categoriesFragment = CategoriesFragment()
    private var activeDisplayFragment: Fragment = recordsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }
    private fun init(){
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){

        supportFragmentManager.beginTransaction().apply {
            add(binding.mainActivityFragmentContainer.id, recordsFragment)
            add(binding.mainActivityFragmentContainer.id, analysisFragment).hide(analysisFragment)
            add(binding.mainActivityFragmentContainer.id, budgetFragment).hide(budgetFragment)
            add(binding.mainActivityFragmentContainer.id, accountsFragment).hide(accountsFragment)
            add(binding.mainActivityFragmentContainer.id, categoriesFragment).hide(categoriesFragment)
        }.commit()

        setTitleFragment(titleFragment)

        binding.mainActivityBottomNavigation.selectTabAt(0)
    }
    private fun setListeners(){
        binding.mainActivityBottomNavigation.setOnTabInterceptListener(object : AnimatedBottomBar.OnTabInterceptListener {
            override fun onTabIntercepted(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ): Boolean {
                when(newTab.id) {
                    R.id.tab_records -> setDisplayFragment(recordsFragment)
                    R.id.tab_analysis -> setDisplayFragment(analysisFragment)
                    R.id.tab_budget -> setDisplayFragment(budgetFragment)
                    R.id.tab_accounts -> setDisplayFragment(accountsFragment)
                    R.id.tab_categories -> setDisplayFragment(categoriesFragment)
                }
                return true
            }
        })
    }
    private fun setTitleFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.mainActivityTitleLayout.id, fragment)
        }.commit()
    }
    private fun setDisplayFragment(fragment: Fragment){
        if (fragment != activeDisplayFragment){
            supportFragmentManager.beginTransaction().apply {
                hide(activeDisplayFragment)
                show(fragment)
            }.commit()

            activeDisplayFragment = fragment
        }
    }
    private fun toast(message: Any?){
        Toast.makeText(applicationContext,"$message",Toast.LENGTH_SHORT).show()
    }
    private fun log(message: Any?){
        Log.d("MainActivity", "$message")
    }
}