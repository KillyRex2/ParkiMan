package Library;

import android.util.Pair;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import com.example.userskotlin.R;

import Models.BindableString;

public class BindEditText {
    @BindingAdapter({"app:binding"}) //Elemeto para los Campos de txt para vincularce para que ejecuten el sig metodo como si fuera un evento
    public static void bindEditText(EditText view, final BindableString bindableString) {
        Pair<BindableString, TextWatcherAdapter> pair = (Pair) view.getTag(R.id.bound_observable);
        if (pair == null || pair.first != bindableString){
            if(pair != null){
                view.removeTextChangedListener(pair.second);
            }
            TextWatcherAdapter watcher = new TextWatcherAdapter(){  //adaptador que cont los eventos de los campos de texto

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bindableString.setValue(s.toString()); //ingresar valores en el campo de texto
                }
            };
            view.setTag(R.id.bound_observable, new Pair<>(bindableString, watcher));
            view.addTextChangedListener(watcher);
        }
        String newValue = bindableString.getValue();
        if (!view.getText().toString().equals(newValue)){
            view.setText(newValue);
        }
    }
}
