package com.example.todoapp.view.home

import android.content.ClipData.Item
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.domain.TaskListDomain
import com.example.todoapp.view.bottomSheet.BottomSheet


class Home : Fragment(){


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerViewAdapter: HomeListAdapter
    private lateinit var taskArray: ArrayList<TaskListDomain>
    private lateinit var taskListRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Toast.makeText(activity, "el swipe funciona bastante bien", Toast.LENGTH_SHORT).show()
            }
        })

         */


        binding.floatingHomeButton.setOnClickListener {
            showBottomSheet()
        }


        getAllTasks()

    }


    private fun getAllTasks() {
        taskArray = arrayListOf<TaskListDomain>()
        homeViewModel.getAllTasks()
        homeViewModel.getAllTask.observe(viewLifecycleOwner){ transactionList ->
            taskArray.clear()
            transactionList?.map {
                taskArray.add(
                    TaskListDomain(
                        it.id,
                        it.task.toString(),
                        it.dateToString,
                        it.personalOrWork,
                    )
                )
            }


            if (transactionList.isNotEmpty()){
                binding.totalListToDo.visibility = View.VISIBLE
                binding.lottieAnimationView.visibility = View.GONE
                binding.emptyMessage.visibility = View.GONE

                val layoutManager = LinearLayoutManager(context)
                taskListRecyclerView = binding.totalListToDo
                taskListRecyclerView.layoutManager = layoutManager
                taskListRecyclerView.setHasFixedSize(true)
                recyclerViewAdapter = HomeListAdapter(taskArray)
                taskListRecyclerView.adapter = recyclerViewAdapter



                val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT
                ) {
                    private val deleteIcon: Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
                    private val background: ColorDrawable = ColorDrawable(Color.RED)

                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        val taskToDelete = taskArray[position]

                        recyclerViewAdapter.deleteItem(position)
                        taskToDelete.id?.let { homeViewModel.deleteTask(it) }
                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {
                        val itemView = viewHolder.itemView
                        val iconMargin = (itemView.height - deleteIcon?.intrinsicHeight!!) / 2

                        if (dX < 0) {
                            // Swiping to the right
                            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                            deleteIcon?.setBounds(
                                itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                                itemView.top + iconMargin,
                                itemView.right - iconMargin,
                                itemView.bottom - iconMargin
                            )
                        }

                        background.draw(c)
                        deleteIcon?.draw(c)

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    }
                })


                itemTouchHelper.attachToRecyclerView(taskListRecyclerView)


            }
            else{
                binding.totalListToDo.visibility = View.GONE
                binding.lottieAnimationView.visibility = View.VISIBLE
                binding.emptyMessage.visibility = View.VISIBLE
            }

        }


    }

    private fun showBottomSheet() {
        val bottomSheetFragment = BottomSheet("general")
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


