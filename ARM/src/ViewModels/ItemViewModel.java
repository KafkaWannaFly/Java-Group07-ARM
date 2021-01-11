package ViewModels;

import Models.Item;
import Models.ModelManager;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.io.FilenameUtils;
import org.bson.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ItemViewModel {
	Item item;

	public ItemViewModel(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItemName(String name) {
		this.item.setName(name);
	}

	public void setItemPrice(String price) {
		this.item.setPrice(Long.parseLong(price));
	}

	/**
	 * Check if image is in local machine
	 * If not, download
	 * else, return the file
	 *
	 * @param item item object that hold info
	 * @return file represented for image
	 */
	public CompletableFuture<File> getImageAsync(Item item) {
		return CompletableFuture.supplyAsync(new Supplier<File>() {
			@Override
			public File get() {
				try {
					URL url = new URL(item.getImgPath());

					File dir = new File("ItemImages");
					if (!dir.exists()) {
						boolean mkdir = dir.mkdir();
					}

					String localPath = "ItemImages/" + FilenameUtils.getName(url.getPath());
					File file = new File(localPath);
					if (file.exists()) {
						return file;
					}

					try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
					     FileOutputStream fileOutputStream = new FileOutputStream(localPath);) {

						fileOutputStream.getChannel()
								.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

						return new File(localPath);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}
		});
	}
}
