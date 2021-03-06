package com.dappervision.wearscript.launcher;

import android.content.Context;
import android.content.Intent;

import com.dappervision.wearscript.Log;
import com.dappervision.wearscript.Utils;
import com.dappervision.wearscript.WearScript;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class InstalledScripts {
    private static final String TAG = "WearScriptHelper";
    private static final String GISTS_PATH = "gists/";
    private static final String WEARSCRIPT_PATH = Utils.dataPath() + GISTS_PATH;
    private static ArrayList<WearScriptInfo> mWearScripts;

    public InstalledScripts(Context context) {
        load(context);
    }

    private List<String> GistList() {
        File extStorageDir = new File(WEARSCRIPT_PATH);
        Log.i(TAG, "WSFiles: the directory: " + extStorageDir);
        String[] flArray = extStorageDir.list();
        if (flArray == null)
            return new ArrayList<String>();
        List<String> gists = Arrays.asList(flArray);
        Collections.sort(gists);
        Collections.reverse(gists);
        return gists;
    }

    public void load(Context context) {
        List<String> gists = GistList();
        if (mWearScripts == null) {
            mWearScripts = new ArrayList<WearScriptInfo>(gists.size());
        }
        mWearScripts.clear();
        mWearScripts.add(getBuilder().playground(context));
        mWearScripts.add(getBuilder().stop(context));
        mWearScripts.add(getBuilder().setup(context));
        mWearScripts.add(getBuilder().gistSync(context));
        for (String gist : gists) {
            byte[] manifestData = Utils.LoadData(GISTS_PATH + gist, "manifest.json");
            if (manifestData == null)
                continue;
            JSONObject manifest = (JSONObject) JSONValue.parse(new String(manifestData));
            if (manifest == null || !manifest.containsKey("name") || manifest.get("name").equals(""))
                continue;
            String filePath = WEARSCRIPT_PATH + gist + "/" + "glass.html";
            WearScriptInfo wsInfo = getBuilder().buildInfo(context, (String) manifest.get("name"), filePath);
            mWearScripts.add(wsInfo);
        }
    }

    protected abstract WearScriptInfo getBuilder();

    public ArrayList<WearScriptInfo> getWearScripts() {
        return mWearScripts;
    }

}
