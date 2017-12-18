package com.co.example.dbutil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.co.example.entity.product.TBrProductSpec;
import com.co.example.entity.spec.TBrSpecValue;
import com.github.moncat.common.generator.id.NextId;
import com.google.common.collect.Maps;
import com.sun.rowset.CachedRowSetImpl;

public class TestData {
	public static List<TBrProductSpec> findAllTmp(String sql) {
		List<TBrProductSpec> list = new ArrayList<TBrProductSpec>();
		TBrProductSpec tBrProductSpec = null;
		DBUtil dbUtil = null;
		Long pid = null;
		String specValueName = null;
		CachedRowSetImpl rs = null;
		try {
			dbUtil = new DBUtil();
			rs = dbUtil.query(sql);
			while (rs.next()) {
				pid = rs.getLong("pid");
				specValueName = rs.getString("spec_value_name");
				tBrProductSpec = new TBrProductSpec();
				tBrProductSpec.setPid(pid);
				tBrProductSpec.setSpecValueName(specValueName);
				list.add(tBrProductSpec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				dbUtil.close()
				;
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return list;
	}
	
		public static List<TBrSpecValue> findAllTmp2(String sql) {
		List<TBrSpecValue> list = new ArrayList<TBrSpecValue>();
		TBrSpecValue tBrSpecValue = null;
		DBUtil dbUtil = null;
		Long tmpId = null;
		String valueName = null;
		CachedRowSetImpl rs = null;
		try {
			dbUtil = new DBUtil();
			rs = dbUtil.query(sql);
			while (rs.next()) {
				tmpId = rs.getLong("id");
				valueName = rs.getString("value_name");
				tBrSpecValue = new TBrSpecValue();
				tBrSpecValue.setId(tmpId);
				tBrSpecValue.setValueName(valueName);
				list.add(tBrSpecValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				dbUtil.close()
				;
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static void delData(Long id) {
		DBUtil dbUtil = null;
		String sql = "delete from t_br_product_image where id=" + id;
		try {
			dbUtil = new DBUtil();
			dbUtil.delete(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}

	}
	

	
	public static void insertData(Map params) {
		DBUtil dbUtil = null;
		String sql = "INSERT INTO t_br_product_spec"+
	"(`id`, `pid`, `spec_key_id`, `spec_key_name`, `spec_value_id`, `spec_value_name`, `remark`, `source`, `more_data1`, `more_data2`, `item_order`, `create_time`, `create_by`, `update_time`, `update_by`, `is_active`, `del_flg`) VALUES"+ 
	"(?, ?, '2389971818184706', '功效', ?, ?, NULL, '4', NULL, NULL, NULL, '2017-11-27 16:56:19', NULL, NULL, NULL, '1', '0'); ";
		try {
			dbUtil = new DBUtil();
			dbUtil.insert(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}

	}
	
	public static void main(String[] args) {
		String sql = "select * from tmp2";
		List<TBrProductSpec> list = findAllTmp(sql);
		
		String valueName = null;
		String[] split = null;
		HashMap<String, Object> map = Maps.newHashMap();
		for (TBrProductSpec one : list) {
			valueName = one.getSpecValueName();
			valueName = valueName.replace(" ",",");
			split = valueName.split(",");
			for (String str : split) {
				String sql2 = "select * from t_br_spec_value where value_name like '%"+str+"%'";
				List<TBrSpecValue> list2 = findAllTmp2(sql2);
				if(list2.size()>0){
					TBrSpecValue tBrSpecValue = list2.get(0);
					map.put("id", NextId.getId());
					map.put("pid", one.getPid());
					map.put("spec_value_id", tBrSpecValue.getId());
					map.put("spec_value_name", tBrSpecValue.getValueName());
					insertData(map);
				}
			}
		}
	}
	
	
	
	
}






