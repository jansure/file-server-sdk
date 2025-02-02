package pkpm.desktop.cloud.search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Count;
import io.searchbox.core.CountResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;

@Service
public class JestService {

	@Autowired
	JestClient jestClient;

	/**
	 * 创建索引
	 * 
	 * @param jestClient
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean createIndex(String indexName) throws Exception {

		JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
		return jr.isSucceeded();
	}

	/**
	 * Put映射
	 * 
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public boolean createIndexMapping(String indexName, String typeName, String source) throws Exception {

		PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
		JestResult jr = jestClient.execute(putMapping);
		return jr.isSucceeded();
	}

	/**
	 * Get映射
	 * 
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @return
	 * @throws Exception
	 */
	public String getIndexMapping(String indexName, String typeName) throws Exception {

		GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
		JestResult jr = jestClient.execute(getMapping);
		return jr.getJsonString();
	}

	/**
	 * 索引文档
	 * 
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public boolean index(String indexName, String typeName, List<Object> objs) throws Exception {

		Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
		for (Object obj : objs) {
			Index index = new Index.Builder(obj).build();
			bulk.addAction(index);
		}
		String ELASTIC_SEARCH_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
		Gson gson = new GsonBuilder()
	            .setDateFormat(ELASTIC_SEARCH_DATE_FORMAT)
	            .create();
		String str = bulk.build().getData(gson);
		BulkResult br = jestClient.execute(bulk.build());
		return br.isSucceeded();
	}

	/**
	 * 搜索文档
	 * 
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public SearchResult search(String indexName, String typeName, String query) throws Exception {

		Search search = new Search.Builder(query).addIndex(indexName).addType(typeName).build();
		return jestClient.execute(search);
	}

	/**
	 * Count文档
	 * 
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Double count(String indexName, String typeName, String query) throws Exception {

		Count count = new Count.Builder().addIndex(indexName).addType(typeName).query(query).build();
		CountResult results = jestClient.execute(count);
		return results.getCount();
	}

	/**
	 * Get文档
	 * 
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JestResult get(String indexName, String typeName, String id) throws Exception {

		Get get = new Get.Builder(indexName, id).type(typeName).build();
		return jestClient.execute(get);
	}

	/**
	 * Delete索引
	 * 
	 * @param jestClient
	 * @param indexName
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String indexName) throws Exception {

		JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
		return jr.isSucceeded();
	}

	/**
	 * Delete文档
	 * 
	 * @param jestClient
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String indexName, String typeName, String id) throws Exception {

		DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
		return dr.isSucceeded();
	}

	/**
	 * 关闭JestClient客户端
	 * 
	 * @param jestClient
	 * @throws Exception
	 */
	public void closeJestClient() throws Exception {

		if (jestClient != null) {
			jestClient.shutdownClient();
		}
	}
}