package com.ketian.android.silkv3;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ketian.android.silkv3.jni.JNI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by ketian.
 */
public class VoiceFragment extends Fragment {

    private LoadTask mTask = null;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;

    private List<String> mItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voice, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItemAdapter(getContext(), mItems);
        mRecyclerView.setAdapter(mAdapter);

        initData();
    }

    private void initData() {
        if (mTask != null) {
            mTask.cancel(true);
        }

        mTask = new LoadTask(getContext());
        mTask.execute();
    }

    private class LoadTask extends AsyncTask<Void, Void, List<String>> {

        private Context mContext;

        public LoadTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> paths = PathUtils.getVoiceFiles_WeChat(mContext);
            ArrayList<String> voicePaths = new ArrayList<>();

            if (paths != null && paths.size() > 0) {
                File file;
                for (String path : paths) {
                    if (path != null) {
                        file = new File(path);
                        if (file != null && file.exists() && file.isDirectory()) {
                            Stack<String> stack = new Stack<>();
                            stack.push(path);
                            while (!stack.empty()) {
                                File[] fs = null;
                                String parent = stack.pop();
                                if (parent != null) {
                                    file = new File(parent);
                                    if (file.isDirectory()) { // ignore file, FIXME
                                        fs = file.listFiles();
                                    } else {
                                        continue;
                                    }
                                }
                                if (fs == null || fs.length == 0) continue;
                                for (int i = 0; i < fs.length; ++i) {
                                    final String name = fs[i].getName();
                                    if (fs[i].isDirectory() && !name.equals(".")
                                            && !name.equals("..")) {
                                        stack.push(fs[i].getPath());
                                    } else if (fs[i].isFile()) {
                                        if (name.endsWith(".amr")) {
                                            voicePaths.add(fs[i].getAbsolutePath());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


            return voicePaths;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            mItems.clear();
            mItems.addAll(strings);

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mTask != null) {
            mTask.cancel(true);
        }
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.VH> {

        private List<String> paths;
        private Context mContext;

        private View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = (int) view.getTag();
                String path = paths.get(index);
                File file = new File(path);
                if (file != null && file.exists() && file.canRead()) {
                    String dest = PathUtils.getExportDir() + "/" + file.getName() + ".mp3";
                    JNI.x(path, dest);
                    Toast.makeText(mContext, "Convert to " + dest + " OK", Toast.LENGTH_SHORT).show();
                }
            }
        };

        public ItemAdapter(Context context, List<String> items) {
            mContext = context;
            paths = items;
        }

        @Override
        public ItemAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(ItemAdapter.VH holder, int position) {
            holder.textView.setText(paths.get(position));
            holder.button.setTag(position);
            holder.button.setOnClickListener(listener);
        }

        @Override
        public int getItemCount() {
            return paths != null ? paths.size() : 0;
        }

        public static class VH extends RecyclerView.ViewHolder {
            public TextView textView;
            public Button button;

            public VH(View itemView) {
                super(itemView);

                textView = (TextView) itemView.findViewById(R.id.title);
                button = (Button) itemView.findViewById(R.id.click);
            }
        }
    }
}
