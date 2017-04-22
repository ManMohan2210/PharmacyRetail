
package mypackage.loginregistration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddMedicine extends BaseActivty {
    @Bind(R.id.edt_medicine_name)
    EditText mEdtMedicine;
    @Bind(R.id.edt_medicine_description)
    EditText mEdtMedicineDesc;
    @Bind(R.id.medicine_img)
    ImageView imgMedicine;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        setContentView(R.layout.add_medicines);
        ButterKnife.bind(this);
    }
}
