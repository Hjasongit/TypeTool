package com.type.tool.action;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.fy.sqlparam.impl.SqlParameter;
import com.fy.sqlparam.param.ISqlParameter;
import com.fy.toolhelper.tool.ActionTool;
import com.fy.toolhelper.util.RequestUtils;
import com.type.tool.dao.ITypeDao;
import com.type.tool.dao.impl.TypeDaoImpl;
import com.type.tool.entity.Type;

public class TypeTool extends ActionTool {

	private static final Logger logger = Logger.getLogger(TypeTool.class);

	public final static String GLB_KEY_AID = "GLB_KEY_AID";
	public final static String GLB_KEY_MESSAGE_TOOL_ID = "GLB_KEY_MESSAGE_TOOL_ID";
	public final static String GLB_KEY_WTB_CORE_URL = "GLB_KEY_WTB_CORE_URL";
	public final static String TMP_KEY_CURRENT_USER_ID = "CURRENT_USER_ID";
	public final static String TMP_KEY_RUN_TOOL_BAND_ID = "RUN_TOOL_BAND_ID";
	public final static String TMP_KEY_ACCESS_TOKEN = "TMP_KEY_ACCESS_TOKEN";
	public final static String TMP_KEY_GID = "TMP_KEY_GID";
	public final static String TMP_KEY_TOOL_ID = "TMP_KEY_TOOL_ID";

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	// SimpleDateFormat simpleDateFormat = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

	@Override
	protected void onAfterAct(String arg0, Object arg1) throws Exception {
		logger.info("接口调用完毕！");
	}

	@Override
	protected String onBeforeAct(HttpServletRequest request,
			HttpServletResponse response, TemporyContext context,
			String runToolParamStr) throws Exception {
		context.putTempory(TMP_KEY_ACCESS_TOKEN, this.accessToken);
		context.putTempory(TMP_KEY_RUN_TOOL_BAND_ID, this.getbViewId());
		context.putTempory(TMP_KEY_GID, this.getbViewId());
		context.putTempory(TMP_KEY_CURRENT_USER_ID, this.getUserID());
		context.putTempory(TMP_KEY_TOOL_ID, this.getToolId());

		return RequestUtils.getStringParameter(request, "action");
	}

	@Override
	protected Object onCatchException(Throwable arg0) throws Exception {
		Map<String, Object> result = new HashMap<>();

		result.put("msg", arg0.getMessage());
		result.put("stack", arg0.getStackTrace());

		return result;
	}

	@Override
	protected boolean onInit(GlobalContext context, String configParamStr)
			throws Exception {
		if (configParamStr != null) {
			JSONObject configParamJson = JSONObject.fromObject(configParamStr);
			context.putGlobal(GLB_KEY_AID, configParamJson.get("Aid"));
			context.putGlobal(GLB_KEY_MESSAGE_TOOL_ID,
					configParamJson.get("MessageToolID"));
			context.putGlobal(GLB_KEY_WTB_CORE_URL,
					this.getWtbOpenInterfaceUrl());
			return true;
		}
		return false;
	}

	@Action
	public Map<String, Object> getTypes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISqlParameter parameter = new SqlParameter();
		Connection connection = TypeTool.getDBConnection();
		ITypeDao locationdao = TypeTool.getBean(ITypeDao.class);
		List<Type> types = locationdao.getTypes(connection, parameter);
		Map<String, Object> result = new HashMap<>();
		result.put("types", types);
		return result;
	}

	@Action
	public Map<String, Object> addType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection connection = getDBConnection();
		String catagoryName = RequestUtils.getStringParameter(request,
				"catagoryName");
		if (catagoryName == null || "".equals(catagoryName.trim())) {
			throw new Exception("获取 catagoryName 参数为空");
		}
		ITypeDao typeDao = getBean(ITypeDao.class);
		
		Long total = typeDao.getCountByName(connection, catagoryName);
		Map<String, Object> result = new HashMap<>();
		if(total > 0){
			result.put("result", false);
			result.put("total", total);
			result.put("msg", "该类型已有存在, 请勿重复添加！");
		} else {
			Type type = new Type();
			type.setCatagory_name(catagoryName);
			typeDao.save(connection, type);
			
			result.put("result", true);
			result.put("msg", "添加区域成功！");
		}
		return result;
	}

	@Action
	public Map<String, Object> deleteTypeById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = RequestUtils.getLongParameter(request, "id");

		Connection connection = TypeTool.getDBConnection();
		ITypeDao typeDao = TypeTool.getBean(ITypeDao.class);
		
		Long total = typeDao.getCountByIDAndEquip(connection, id) + typeDao
				.getCountByIDAndMaint(connection, id);
		Map<String, Object> result = new HashMap<>();
		
		if(total > 0){
			result.put("result", false);
			result.put("total", total);
			result.put("msg", "该类型已有关联(设备或维修人员), 无法删除！");
		} else {
			result.put("result", true);
			result.put("msg", "删除区域成功！");
			typeDao.deleteByKeyID(connection, id);
		}
		
		result.put("result", true);
		result.put("msg", "删除类型成功！");
		return result;
	}

	@Action
	public Map<String, Object> updateType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Connection connection = getDBConnection();
		Long id = RequestUtils.getLongParameter(request, "id");
		String catagoryName = RequestUtils.getStringParameter(request,
				"catagoryName");

		Type type = new Type();

		if (id != null) {
			type.setId(id);
		}else{
			throw new Exception("获取 id 参数为空");
		}

		if (catagoryName != null && !"".equals(catagoryName.trim())) {
			type.setCatagory_name(catagoryName);
		}
		else{
			throw new Exception("获取 catagoryName 参数为空");
		}
		ITypeDao typeDao = getBean(ITypeDao.class);
		Long total = typeDao.getCountByName(connection, catagoryName);
		Map<String, Object> result = new HashMap<>();
		if(total > 0){
			result.put("result", false);
			result.put("total", total);
			result.put("msg", "该类型已存在，请勿重复添加！");
		}else{
			typeDao.update(connection, type);
			
			result.put("result", true);
			result.put("msg", "更新类型成功！");
		}
		
		return result;
	}

	@BeanDeclare(forClass = ITypeDao.class, scope = BeanScope.GLOBAL)
	private ITypeDao onDeclaringLocationDao() throws Exception {
		return new TypeDaoImpl();
	}
}
