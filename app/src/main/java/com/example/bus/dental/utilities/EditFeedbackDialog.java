package com.example.bus.dental.utilities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bus.dental.R;
import com.example.bus.dental.models.Comment;

public class EditFeedbackDialog extends DialogFragment {
    Button edit,delete;
    EditText fragment_feedback;
    public interface EditFeedback{
        void edit(String feedback,int position);
        void delete(Comment comment,int position);
    }
    public EditFeedback editFeedback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_feedback, container, false);
        edit=view.findViewById(R.id.fragment_feedback_edit);
        delete=view.findViewById(R.id.fragment_feedback_delete);
        fragment_feedback=view.findViewById(R.id.fragment_feedback);
        Comment comment = new Comment();



        String feedback=this.getArguments().getString("feedback");
        int position= Integer.parseInt(this.getArguments().getString("position"));
        fragment_feedback.setText(feedback);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editFeedback.delete(comment,position);
                getDialog().dismiss();

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFeedback=fragment_feedback.getText().toString();
                editFeedback.edit(newFeedback,position);
                getDialog().dismiss();
            }
        });





        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            editFeedback=(EditFeedback) getActivity();
        }catch (ClassCastException e){
            Log.e("aa","onAttach: ClassException: "+e.getMessage());
        }
    }
}
