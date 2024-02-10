package com.example.todoapp.view.toDoList

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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ToDoListBinding
import com.example.todoapp.domain.TaskListDomain
import com.example.todoapp.view.bottomSheet.BottomSheet
import com.example.todoapp.view.home.HomeListAdapter

class ToDoList(private val personalOrWork: String?)  : Fragment() {

    private var _binding : ToDoListBinding? = null
    private val binding get() = _binding!!
    lateinit var toDoListViewModel: ToDoListViewModel
    private lateinit var taskArray: ArrayList<TaskListDomain>
    private lateinit var taskListRecyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ToDoListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toDoListViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ToDoListViewModel::class.java]

        _binding = ToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (personalOrWork == "personal"){
            binding.toDoListAnimation.setAnimation(R.raw.planet)
            binding.toDoListAnimation.playAnimation()
        }

        else if(personalOrWork == "work"){
            binding.toDoListAnimation.setAnimation(R.raw.mars)
            binding.toDoListAnimation.playAnimation()
        }

        getAllPersonalOrWorkTasks(personalOrWork.toString())

        binding.floatingToDoListButton.setOnClickListener {
            showBottomSheet()
        }

    }



    private fun getAllPersonalOrWorkTasks(personalOrWork: String) {
        taskArray = arrayListOf<TaskListDomain>()
        toDoListViewModel.setPersonalOrWorkFilter(personalOrWork)
        toDoListViewModel.getEspecificTasks.observe(viewLifecycleOwner) { transactionList ->
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

            Log.d("HomeFragment", "Number of tasks: ${taskArray.size}")

            if (taskArray.isEmpty()) {
                binding.taskList.visibility = View.GONE
                binding.toDoListAnimation.visibility = View.VISIBLE
                binding.emptyMessage.visibility = View.VISIBLE
            } else {
                binding.taskList.visibility = View.VISIBLE
                binding.toDoListAnimation.visibility = View.GONE
                binding.emptyMessage.visibility = View.GONE

                val layoutManager = LinearLayoutManager(context)
                taskListRecyclerView = binding.taskList
                taskListRecyclerView.layoutManager = layoutManager
                taskListRecyclerView.setHasFixedSize(true)
                recyclerViewAdapter = ToDoListAdapter(taskArray)
                taskListRecyclerView.adapter = recyclerViewAdapter


                /************************************/


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
                        taskToDelete.id?.let { toDoListViewModel.deleteTask(it) }
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


                /************************************/


            }
        }
    }


    private fun showBottomSheet() {
        val bottomSheetFragment = BottomSheet(personalOrWork)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

