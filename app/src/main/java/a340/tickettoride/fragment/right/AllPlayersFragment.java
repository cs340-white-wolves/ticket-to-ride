package a340.tickettoride.fragment.right;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import a340.tickettoride.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllPlayersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllPlayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllPlayersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AllPlayersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllPlayersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllPlayersFragment newInstance(String param1, String param2) {
        AllPlayersFragment fragment = new AllPlayersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_all_players, container, false);

        TableLayout table = inflate.findViewById(R.id.playerInfoTable);

        TableLayout.LayoutParams lp_row = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams lp_text = new TableRow.LayoutParams(0,
                TableRow.LayoutParams.WRAP_CONTENT, 1);


        TableRow row = new TableRow(getContext());
        row.setWeightSum(5);
        row.setLayoutParams(lp_row);
        row.setPadding(0, 0, 0, 10);

        String s1 = "player_one";
        String s2 = "100";
        String s3 = "30";
        String s4 = "10";
        String s5 = "3";

        TextView player = new TextView(getContext());
        player.setText(s1);
        player.setLayoutParams(lp_text);
        player.setTextColor(getResources().getColor(R.color.Red));
        row.addView(player);

        TextView points = new TextView(getContext());
        points.setText(s2);
        points.setLayoutParams(lp_text);
        points.setTextColor(getResources().getColor(R.color.Red));
        row.addView(points);

        TextView trains = new TextView(getContext());
        trains.setText(s3);
        trains.setLayoutParams(lp_text);
        trains.setTextColor(getResources().getColor(R.color.Red));
        row.addView(trains);

        TextView cards = new TextView(getContext());
        cards.setText(s4);
        cards.setLayoutParams(lp_text);
        cards.setTextColor(getResources().getColor(R.color.Red));
        row.addView(cards);

        TextView routes = new TextView(getContext());
        routes.setText(s5);
        routes.setLayoutParams(lp_text);
        routes.setTextColor(getResources().getColor(R.color.Red));
        row.addView(routes);

        table.addView(row);

        return inflate;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
