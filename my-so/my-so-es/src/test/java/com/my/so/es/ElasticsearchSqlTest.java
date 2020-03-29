package com.my.so.es;

public class ElasticsearchSqlTest {
	
	public static void main(String[] args) {
		try {
			testJDBC();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testJDBC() throws Exception {
		/*
		String indexName="user";
        Properties properties = new Properties();
        properties.put("url", "jdbc:elasticsearch://127.0.0.1:9300/" + indexName);
        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
        Connection connection = dds.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT  * from  " + indexName + " where sex=0");
        ResultSet resultSet = ps.executeQuery();
        List<String> result = new ArrayList<String>();
        while (resultSet.next()) {
              System.out.println(resultSet.getString("userName") + ","  + resultSet.getLong("id"));
        }
        ps.close();
        connection.close();
        dds.close();
        */
    }
}
