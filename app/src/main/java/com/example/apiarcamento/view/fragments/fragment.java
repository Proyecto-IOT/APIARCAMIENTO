    package com.example.apiarcamento.view.fragments;

    import android.content.Intent;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;

    import com.example.apiarcamento.R;
    import com.example.apiarcamento.view.signup;
    import com.google.android.material.bottomsheet.BottomSheetDialogFragment;




    public class fragment extends BottomSheetDialogFragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment, container, false);

            Button btnAceptar = view.findViewById(R.id.btnAceptar);

            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registrarse = new Intent(getActivity(), signup.class);
                    startActivity(registrarse);
                    dismiss(); // Esto cerrará el fragmento
                }
            });

            return view;
        }
    }