package com.gtdbrowser.data.factory;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gtdbrowser.data.model.Attack;

public class AttackListJsonFactory {

	public static ArrayList<Attack> parseResult(final String wsResponse) throws JSONException {
		final ArrayList<Attack> attackList = new ArrayList<Attack>();

		final JSONObject parser = new JSONObject(wsResponse);
		final JSONArray jsonAttackArray = parser.getJSONArray("objects");
		final int size = jsonAttackArray.length();
		for (int i = 0; i < size; i++) {
			final JSONObject jsonAttack = jsonAttackArray.getJSONObject(i);
			final Attack attack = new Attack();

			if (!jsonAttack.isNull("addnotes"))
				attack.addnotes = jsonAttack.getString("addnotes");
			if (!jsonAttack.isNull("alternative"))
				attack.alternative = jsonAttack.getInt("alternative");
			if (!jsonAttack.isNull("approxdate"))
				attack.approxdate = jsonAttack.getString("approxdate");
			if (!jsonAttack.isNull("attacktype1"))
				attack.attacktype1 = jsonAttack.getString("attacktype1");
			if (!jsonAttack.isNull("attacktype2"))
				attack.attacktype2 = jsonAttack.getString("attacktype2");
			if (!jsonAttack.isNull("attacktype3"))
				attack.attacktype3 = jsonAttack.getString("attacktype3");
			if (!jsonAttack.isNull("city"))
				attack.city = jsonAttack.getString("city");
			if (!jsonAttack.isNull("claim2"))
				attack.claim2 = jsonAttack.getBoolean("claim2");
			if (!jsonAttack.isNull("claim3"))
				attack.claim3 = jsonAttack.getBoolean("claim3");
			if (!jsonAttack.isNull("claimconf"))
				attack.claimconf = jsonAttack.getBoolean("claimconf");
			if (!jsonAttack.isNull("claimconf2"))
				attack.claimconf2 = jsonAttack.getBoolean("claimconf2");
			if (!jsonAttack.isNull("claimconf3"))
				attack.claimconf3 = jsonAttack.getBoolean("claimconf3");
			if (!jsonAttack.isNull("claimed"))
				attack.claimed = jsonAttack.getBoolean("claimed");
			if (!jsonAttack.isNull("claimmode"))
				attack.claimmode = jsonAttack.getString("claimmode");
			if (!jsonAttack.isNull("claimmode2"))
				attack.claimmode2 = jsonAttack.getString("claimmode2");
			if (!jsonAttack.isNull("claimmode3"))
				attack.claimmode3 = jsonAttack.getString("claimmode3");
			if (!jsonAttack.isNull("compclaim"))
				attack.compclaim = jsonAttack.getBoolean("compclaim");
			if (!jsonAttack.isNull("conflict"))
				attack.conflict = jsonAttack.getBoolean("conflict");
			if (!jsonAttack.isNull("corp1"))
				attack.corp1 = jsonAttack.getString("corp1");
			if (!jsonAttack.isNull("corp2"))
				attack.corp2 = jsonAttack.getString("corp2");
			if (!jsonAttack.isNull("corp3"))
				attack.corp3 = jsonAttack.getString("corp3");
			if (!jsonAttack.isNull("country"))
				attack.country = jsonAttack.getString("country");
			if (!jsonAttack.isNull("crit1"))
				attack.crit1 = jsonAttack.getBoolean("crit1");
			if (!jsonAttack.isNull("crit2"))
				attack.crit2 = jsonAttack.getBoolean("crit2");
			if (!jsonAttack.isNull("crit3"))
				attack.crit3 = jsonAttack.getBoolean("crit3");
			if (!jsonAttack.isNull("date"))
				attack.date = jsonAttack.getString("date");
			if (!jsonAttack.isNull("dbsource"))
				attack.dbsource = jsonAttack.getString("dbsource");
			if (!jsonAttack.isNull("divert"))
				attack.divert = jsonAttack.getString("divert");
			if (!jsonAttack.isNull("doubtterr"))
				attack.doubtterr = jsonAttack.getBoolean("doubtterr");
			if (!jsonAttack.isNull("extended"))
				attack.extended = jsonAttack.getBoolean("extended");
			if (!jsonAttack.isNull("gname"))
				attack.gname = jsonAttack.getString("gname");
			if (!jsonAttack.isNull("gname2"))
				attack.gname2 = jsonAttack.getString("gname2");
			if (!jsonAttack.isNull("gname3"))
				attack.gname3 = jsonAttack.getString("gname3");
			if (!jsonAttack.isNull("gsubname"))
				attack.gsubname = jsonAttack.getString("gsubname");
			if (!jsonAttack.isNull("gsubname2"))
				attack.gsubname2 = jsonAttack.getString("gsubname2");
			if (!jsonAttack.isNull("gsubname3"))
				attack.gsubname3 = jsonAttack.getString("gsubname3");
			if (!jsonAttack.isNull("guncertain1"))
				attack.guncertain1 = jsonAttack.getBoolean("guncertain1");
			if (!jsonAttack.isNull("guncertain2"))
				attack.guncertain2 = jsonAttack.getBoolean("guncertain2");
			if (!jsonAttack.isNull("guncertain3"))
				attack.guncertain3 = jsonAttack.getBoolean("guncertain3");
			if (!jsonAttack.isNull("hostkidoutcome"))
				attack.hostkidoutcome = jsonAttack.getString("hostkidoutcome");
			if (!jsonAttack.isNull("id"))
				attack.id = jsonAttack.getString("id");
			if (!jsonAttack.isNull("ishostkid"))
				attack.ishostkid = jsonAttack.getBoolean("ishostkid");
			if (!jsonAttack.isNull("kidhijcountry"))
				attack.kidhijcountry = jsonAttack.getString("kidhijcountry");
			if (!jsonAttack.isNull("location"))
				attack.location = jsonAttack.getString("location");
			if (!jsonAttack.isNull("motive"))
				attack.motive = jsonAttack.getString("motive");
			if (!jsonAttack.isNull("multiple"))
				attack.multiple = jsonAttack.getBoolean("multiple");
			if (!jsonAttack.isNull("ndays"))
				attack.ndays = jsonAttack.getInt("ndays");
			if (!jsonAttack.isNull("nhostkid"))
				attack.nhostkid = jsonAttack.getInt("nhostkid");
			if (!jsonAttack.isNull("nhostkidus"))
				attack.nhostkidus = jsonAttack.getInt("nhostkidus");
			if (!jsonAttack.isNull("nhours"))
				attack.nhours = jsonAttack.getInt("nhours");
			if (!jsonAttack.isNull("nkill"))
				attack.nkill = jsonAttack.getInt("nkill");
			if (!jsonAttack.isNull("nkillter"))
				attack.nkillter = jsonAttack.getInt("nkillter");
			if (!jsonAttack.isNull("nkillus"))
				attack.nkillus = jsonAttack.getInt("nkillus");
			if (!jsonAttack.isNull("nperpcap"))
				attack.nperpcap = jsonAttack.getInt("nperpcap");
			if (!jsonAttack.isNull("nperps"))
				attack.nperps = jsonAttack.getInt("nperps");
			if (!jsonAttack.isNull("nreleased"))
				attack.nreleased = jsonAttack.getInt("nreleased");
			if (!jsonAttack.isNull("nwound"))
				attack.nwound = jsonAttack.getInt("nwound");
			if (!jsonAttack.isNull("nwoundter"))
				attack.nwoundter = jsonAttack.getInt("nwoundter");
			if (!jsonAttack.isNull("nwoundus"))
				attack.nwoundus = jsonAttack.getInt("nwoundus");
			if (!jsonAttack.isNull("propcomment"))
				attack.propcomment = jsonAttack.getString("propcomment");
			if (!jsonAttack.isNull("property"))
				attack.property = jsonAttack.getBoolean("property");
			if (!jsonAttack.isNull("propextent"))
				attack.propextent = jsonAttack.getString("propextent");
			if (!jsonAttack.isNull("propvalue"))
				attack.propvalue = jsonAttack.getString("propvalue");
			if (!jsonAttack.isNull("provstate"))
				attack.provstate = jsonAttack.getString("provstate");
			if (!jsonAttack.isNull("ransom"))
				attack.ransom = jsonAttack.getBoolean("ransom");
			if (!jsonAttack.isNull("ransomamt"))
				attack.ransomamt = jsonAttack.getString("ransomamt");
			if (!jsonAttack.isNull("ransomamtus"))
				attack.ransomamtus = jsonAttack.getString("ransomamtus");
			if (!jsonAttack.isNull("ransomnote"))
				attack.ransomnote = jsonAttack.getString("ransomnote");
			if (!jsonAttack.isNull("ransompaid"))
				attack.ransompaid = jsonAttack.getString("ransompaid");
			if (!jsonAttack.isNull("ransompaidus"))
				attack.ransompaidus = jsonAttack.getString("ransompaidus");
			if (!jsonAttack.isNull("region"))
				attack.region = jsonAttack.getString("region");
			if (!jsonAttack.isNull("resolution"))
				attack.resolution = jsonAttack.getString("resolution");
			if (!jsonAttack.isNull("resource_uri"))
				attack.resource_uri = jsonAttack.getString("resource_uri");
			if (!jsonAttack.isNull("scite1"))
				attack.scite1 = jsonAttack.getString("scite1");
			if (!jsonAttack.isNull("scite2"))
				attack.scite2 = jsonAttack.getString("scite2");
			if (!jsonAttack.isNull("scite3"))
				attack.scite3 = jsonAttack.getString("scite3");
			if (!jsonAttack.isNull("success"))
				attack.success = jsonAttack.getBoolean("success");
			if (!jsonAttack.isNull("suicide"))
				attack.suicide = jsonAttack.getBoolean("suicide");
			if (!jsonAttack.isNull("summary"))
				attack.summary = jsonAttack.getString("summary");
			if (!jsonAttack.isNull("target1"))
				attack.target1 = jsonAttack.getString("target1");
			if (!jsonAttack.isNull("target2"))
				attack.target2 = jsonAttack.getString("target2");
			if (!jsonAttack.isNull("target3"))
				attack.target3 = jsonAttack.getString("target3");
			if (!jsonAttack.isNull("targtype1"))
				attack.targtype1 = jsonAttack.getString("targtype1");
			if (!jsonAttack.isNull("targtype2"))
				attack.targtype2 = jsonAttack.getString("targtype2");
			if (!jsonAttack.isNull("targtype3"))
				attack.targtype3 = jsonAttack.getString("targtype3");
			if (!jsonAttack.isNull("vicinity"))
				attack.vicinity = jsonAttack.getBoolean("vicinity");
			if (!jsonAttack.isNull("weapdetail"))
				attack.weapdetail = jsonAttack.getString("weapdetail");
			if (!jsonAttack.isNull("weapsubtype1"))
				attack.weapsubtype1 = jsonAttack.getString("weapsubtype1");
			if (!jsonAttack.isNull("weapsubtype2"))
				attack.weapsubtype2 = jsonAttack.getString("weapsubtype2");
			if (!jsonAttack.isNull("weapsubtype3"))
				attack.weapsubtype3 = jsonAttack.getString("weapsubtype3");
			if (!jsonAttack.isNull("weapsubtype4"))
				attack.weapsubtype4 = jsonAttack.getString("weapsubtype4");
			if (!jsonAttack.isNull("weaptype1"))
				attack.weaptype1 = jsonAttack.getString("weaptype1");
			if (!jsonAttack.isNull("weaptype2"))
				attack.weaptype2 = jsonAttack.getString("weaptype2");
			if (!jsonAttack.isNull("weaptype3"))
				attack.weaptype3 = jsonAttack.getString("weaptype3");
			if (!jsonAttack.isNull("weaptype4"))
				attack.weaptype4 = jsonAttack.getString("weaptype4");

			attackList.add(attack);
		}

		return attackList;
	}
}
