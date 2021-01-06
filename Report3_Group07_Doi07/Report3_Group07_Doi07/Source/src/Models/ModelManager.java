package Models;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ModelManager {
	private static volatile ModelManager singleton;

	private MongoDatabase database = null;

	private ModelManager() {
		ConnectionString connString = new ConnectionString(
				"mongodb+srv://ManDuy:ManDuy177013@rootcluster.7m3s7.mongodb.net/RestatouilleDB?retryWrites=true&w=majority"
		);
		MongoClientSettings settings = MongoClientSettings.builder()
				                               .applyConnectionString(connString)
				                               .retryWrites(true)
				                               .build();
		MongoClient mongoClient = MongoClients.create(settings);
		database = mongoClient.getDatabase("RestatouilleDB");

		// deptraicogilasai
	}

	public static ModelManager getInstance() {
		if(singleton == null) {
			singleton = new ModelManager();
		}
		return singleton;
	}

	public MongoDatabase getDatabase() {
		return database;
	}
}
