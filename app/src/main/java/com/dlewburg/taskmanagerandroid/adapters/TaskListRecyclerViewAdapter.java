package com.dlewburg.taskmanagerandroid.adapters;

import static com.dlewburg.taskmanagerandroid.MainActivity.TASK_DESCRIPTION_TAG;
import static com.dlewburg.taskmanagerandroid.MainActivity.TASK_DETAILS_TITLE_TAG;
import static com.dlewburg.taskmanagerandroid.MainActivity.TASK_STATUS_TAG;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;
import com.dlewburg.taskmanagerandroid.MainActivity;
import com.dlewburg.taskmanagerandroid.R;
import com.dlewburg.taskmanagerandroid.activities.TaskDetailsActivity;


import java.util.List;

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskListViewHolder> {

    private final List<Task> tasks;
    Context callingActivity;

    public TaskListRecyclerViewAdapter(List<Task> tasks, Context callingActivity) {
        this.tasks = tasks;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list, parent, false);
        return new TaskListViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        Button taskFragmentButton = holder.itemView.findViewById(R.id.fragmentTaskListButton);
        Task task = tasks.get(position);
//        String taskName = tasks.getTitle();
//        taskFragmentButton.setText(taskName);

        setupTaskButton(taskFragmentButton, task);
//        String taskLatitude = tasks.get(position).getLatitude();
//        String taskLongitude = tasks.get(position).getLongitude();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setupTaskButton(Button goToTaskButton, Task task) {
        goToTaskButton.setOnClickListener(v -> {
            Intent goToTaskDetailsIntent = new Intent(callingActivity, TaskDetailsActivity.class);

            String taskName = task.getTitle();
            goToTaskDetailsIntent.putExtra(TASK_DETAILS_TITLE_TAG, taskName);

            String taskDescription = task.getBody();
            goToTaskDetailsIntent.putExtra(TASK_DESCRIPTION_TAG, taskDescription);
            String taskStatus = task.getStatus().toString();
            goToTaskDetailsIntent.putExtra(TASK_STATUS_TAG, taskStatus);
//            goToTaskDetailsIntent.putExtra(MainActivity.TASK_LATITUDE_EXTRA_TAG, taskLatitude);
//            goToTaskDetailsIntent.putExtra(MainActivity.TASK_LONGITUDE_EXTRA_TAG, taskLongitude);


            callingActivity.startActivity(goToTaskDetailsIntent);
        });
    }


    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TaskListViewHolder(View fragmentItemView) {
            super(fragmentItemView);
        }
    }

}
