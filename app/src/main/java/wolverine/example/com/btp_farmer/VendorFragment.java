package wolverine.example.com.btp_farmer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class VendorFragment extends Fragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static VendorFragment newInstance() {
        VendorFragment fragment = new VendorFragment();
        return fragment;
    }

    public VendorFragment () {
    }
    Button btnFertilizer,btnMachinery,btnSeed,btnPesticide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vendor, container,
                false);
        btnFertilizer = (Button)rootView.findViewById(R.id.btn_fertilizerStore);
        btnMachinery =(Button)rootView.findViewById(R.id.btn_machineryStore);
        btnPesticide = (Button)rootView.findViewById(R.id.btn_pesticideStore);
        btnSeed = (Button)rootView.findViewById(R.id.btn_seedStore);
        btnFertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Here you will find Dealers of fretilizers,the chemical substances added to soil to increase its fertility.Confirm?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent iFert = new Intent(getActivity(),StateDistrictForVendor.class);
                                iFert.putExtra("Tag","Fertilizer");
                                startActivity(iFert);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        btnMachinery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Here you will find Dealers of Machines used in Agriculture.Confirm?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent iMach = new Intent(getActivity(),StateDistrictForVendor.class);
                                iMach.putExtra("Tag","Machinery");
                                startActivity(iMach);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        btnPesticide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Here you will find Dealers of Pesticides,insecticides,DDTs.Confirm?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent iPest = new Intent(getActivity(),StateDistrictForVendor.class);
                                iPest.putExtra("Tag","Pesticide");
                                startActivity(iPest);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        btnSeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Here you will find Dealers of Seeds.Confirm?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent iSeed = new Intent(getActivity(),StateDistrictForVendor.class);
                                iSeed.putExtra("Tag","Seed");
                                startActivity(iSeed);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Navigationfarmer) activity).onSectionAttached(4);
    }
}