package com.example.moneytracker.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytracker.AddRecordActivity
import com.example.moneytracker.R
import com.example.moneytracker.adapters.RecordItemAdapter
import com.example.moneytracker.dataModels.RecordItemModel
import com.example.moneytracker.database.RecordDatabase
import com.example.moneytracker.databaseClients.RecordDatabaseClient
import com.example.moneytracker.databinding.FragmentRecordsBinding
import kotlinx.coroutines.launch

class RecordsFragment : Fragment() {
    private lateinit var binding: FragmentRecordsBinding

    private lateinit var gestureDetector: GestureDetectorCompat
    private var isFabHidden = false

    private lateinit var db: RecordDatabase
    private lateinit var recordsList: MutableList<RecordItemModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordsBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }
    private fun init(){
        initializeUiElements()
        setListeners()
    }
    private fun initializeUiElements(){
        gestureDetector = GestureDetectorCompat(requireContext(), SwipeGestureListener())

        db = RecordDatabaseClient.getInstance(requireContext())

        recordsList = mutableListOf()

        lifecycleScope.launch {
            recordsList = db.recordDao().getAllRecords().toMutableList()

            setAdapters()
        }
    }
    private fun setListeners(){
        binding.recordsFragmentRecordList.addOnItemTouchListener(
            RecyclerViewTouchListener(requireContext(), binding.recordsFragmentRecordList, gestureDetector)
        )
        binding.recordsFragmentRecordList.performClick()
        binding.recordsFragmentFab.setOnClickListener {
            val intent = Intent(requireContext(), AddRecordActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setAdapters(){
        val adapter = RecordItemAdapter(requireActivity(),recordsList)
        binding.recordsFragmentRecordList.layoutManager = LinearLayoutManager(requireContext())
        binding.recordsFragmentRecordList.adapter = adapter
    }

    inner class RecyclerViewTouchListener(
        context: Context,
        recyclerView: RecyclerView,
        private val gestureDetector: GestureDetectorCompat) : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(e)
        }
        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            // Optional: Handle touch event if needed
        }
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            // Optional: Handle request to disallow intercept
        }
    }
    inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            super.onFling(e1, e2, velocityX, velocityY)
            if (e1 == null) {
                return false
            }
            val deltaY = e2.y - e1.y
            if (deltaY > 0) {
                if (isFabHidden) {
                    isFabHidden = false
                    swipeDownFab()
                }
            } else {
                if (!isFabHidden) {
                    isFabHidden = true
                    swipeUpFab()
                }
            }

            return true
        }
        private fun swipeUpFab() {
            val translateAnimation = TranslateAnimation(0f, 0f, 0f, 200f)
            translateAnimation.duration = 300
            translateAnimation.fillAfter = true
            binding.recordsFragmentFab.startAnimation(translateAnimation)
            binding.recordsFragmentFab.isClickable = false
        }
        private fun swipeDownFab() {
            val translateAnimation = TranslateAnimation(0f, 0f, 200f, 0f)
            translateAnimation.duration = 300
            translateAnimation.fillAfter = true
            binding.recordsFragmentFab.startAnimation(translateAnimation)
            binding.recordsFragmentFab.isClickable = true
        }
    }
    private fun toast(message: Any?){
        Toast.makeText(requireContext(),"$message", Toast.LENGTH_SHORT).show()
    }
    private fun log(message: Any?){
        Log.d("RecordsFragment", "$message")
    }
}