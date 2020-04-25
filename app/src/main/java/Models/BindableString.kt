package Models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import java.util.*

class BindableString : BaseObservable() {
    private var value : String ?= null;
    /**
     * BaseObservable que puede ampliar. La clase de datos es responsable de notificar
     * cuando cambien las propiedades. Esto se hace asignado una anotacion @Bindable al captador
     * notificandolo en el definifor este oyente se invoca en cada actualizacion y actualiza
     * las vistas correspondentes.
     */
    @Bindable
    fun getValue():String{
        return if (value != null) value!! else "" // para obtener datos del campo de texto "value" si es = null es = ""
    }
    fun setValue(value : String){
        if (!Objects.equals(this.value, value)){
            this.value = value
            notifyPropertyChanged(BR.value)
        }
    }
}