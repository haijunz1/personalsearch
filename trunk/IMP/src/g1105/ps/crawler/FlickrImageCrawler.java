package g1105.ps.crawler;

import g1105.ps.constant.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.aetrion.flickr.REST;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.Size;


public class FlickrImageCrawler extends Thread{
	private static String apiKey = "5555f3b2a909e11d53bdff8028ea83fc";
	private static String sharedSecret = "74a6df941a349f7e";
	private PhotosInterface photosInterface;
	private List<String> pidList;
	private final String imagePath = Constant.flickPicDir;
	private final String imageReferPath = Constant.flickRefer;
	private SessionFactory sessionFactory;
	private Session session;
		
	public FlickrImageCrawler() {
		try {
			photosInterface = new PhotosInterface(apiKey, sharedSecret,
					new REST());
		} catch (Exception e) {
			e.printStackTrace();
		}
		iniDatabaseSession();
	}
	
	private void iniDatabaseSession(){
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();
	}
	
	public void setTask(List<String> pidList){
		this.pidList = pidList;
	};
	
	public void downloadPhotosByPicId(List<String> pidList) {
		byte[] temp = null;
		int length = 0;
		InputStream in = null;
		FileOutputStream fout = null;
		String fullImageFileName;
		for (String pid : pidList) {
			try {
				com.aetrion.flickr.photos.Photo p1 = photosInterface
						.getPhoto(pid);
				String format = p1.getOriginalFormat();
				in = photosInterface.getImageAsStream(p1, Size.SMALL);
				temp = new byte[1024];
				in.read(temp, 0, length);

				fullImageFileName = imagePath + pid + "." + format;
				File imagedir = new File(imagePath);
				if (!imagedir.exists()) {
					imagedir.mkdirs();
				}
				fout = new FileOutputStream(fullImageFileName);
				int len = 0;
				while ((len = in.read(temp)) != -1) {
					fout.write(temp, 0, len);
				}
				syncToDatabase(pid,imageReferPath + pid + "." + format);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			} finally {
				try {
					if(in != null)
						in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if(fout != null)
						fout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void syncToDatabase(String picId, String picPath) {
		Transaction transaction = session.beginTransaction();
		transaction.begin();

		session.createSQLQuery("UPDATE PicTag1 SET PicPath='" + picPath
				+ "'WHERE PicId=" + picId).executeUpdate();
		transaction.commit();
	};
	
	public void run(){
		downloadPhotosByPicId(pidList);
	}

}
