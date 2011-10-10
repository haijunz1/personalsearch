package g1105.ps.dao;

import g1105.ps.crawler.FlickrImageCrawler;
import g1105.ps.crawler.MSBingImageCrawler;
import g1105.ps.entity.Photo;
import g1105.ps.entity.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PhotoDAO extends ParentDAO {
	private static List<String> nowQuerys = new ArrayList<String>();
	private ArrayList<String> downLoadTask;
	public PhotoDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		downLoadTask = new ArrayList<String>();
	}

	public List<Photo> getPhotoByQuery(Query query,int topN) {
		String formatQuery = "";
		try {
			for (String q : query.getQuery()) {
				formatQuery += q + ",";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		List<Object[]> lt;
		if ((int) formatQuery.charAt(0) > 128){
			String likestr = "";
			try {
				
				for (String q : query.getQuery()) {
					if (likestr == ""){
						likestr = "PicTags like '%"+ q +"%'";
					}
					else{
						likestr += "or PicTags like '%"+ q +"%' ";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			lt = session.createSQLQuery(
					"select PicId,PicTags,PicPath from PicTag2 where "+likestr).list();
		}
		else{
			lt = session.createSQLQuery(
					"select PicId,PicTags,PicPath from PicTag1 where match(PicTags) against('"
							+ formatQuery + "') limit "+ String.valueOf(topN)).list();
		}

		downLoadTask.clear();		
		List<Photo> photos = new ArrayList<Photo>();
		for (Object[] p : lt) {
			Photo photo = new Photo();
			if(p[0]!=null){
				photo.setId(p[0].toString());
			}
			if(p[1]!=null){
				photo.setTags(p[1].toString());
			}
			if(p[2]!=null){
				photo.setPath(p[2].toString());
			}
			Random random = new Random();
			photo.setNprank(String.valueOf(random.nextInt()));
			photo.setPrank(String.valueOf(random.nextInt()));
			photos.add(photo);
			
			if(p[2]==null){
				downLoadTask.add(p[0].toString());
			}
		}
		if(!downLoadTask.isEmpty())
			downloadImagefromFlickr();
		
		int fphotoNum = photos.size();
		if (fphotoNum < topN){
			String bingquery = "";
			for (String q : query.getQuery()) {
				bingquery += q + " ";
			}
			if(!nowQuerys.contains(bingquery)){
				nowQuerys.add(bingquery);
				downloadImagefromBing(topN-fphotoNum,bingquery);
			}
		}
		return photos;
	}

	private void downloadImagefromFlickr(){
		FlickrImageCrawler ficr = new FlickrImageCrawler();
		ficr.setTask(this.downLoadTask);
		ficr.start();
	};
	
	private void downloadImagefromBing(int num,String query){
		MSBingImageCrawler bing = new MSBingImageCrawler();
		bing.setDownloadTotal(num);
		bing.setQuery(query);
		bing.start();
	};
	
	public void addTagToPhoto(String picId, String tag) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		Photo photo = (Photo)session.get(Photo.class, picId);
		String newTags = tag + " " + photo.getTags();
		photo.setTags(newTags);
		session.saveOrUpdate(photo);
		transaction.commit();
		session.close();
	}

	public void addPhoto(Photo photo) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		session.saveOrUpdate(photo);
		transaction.commit();
		session.close();
	}

	public Photo getPhoto(Photo photo) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		photo = (Photo)session.get(Photo.class, photo.getId());
		transaction.commit();
		session.close();
		return photo;
	}
	public List<Photo> getPhotoByLatitudeAndLongitude(String latitude,String longitude){
		List<Photo> photos = new ArrayList<Photo>();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		String sql = "select * from pictag1 where Latitude>"+latitude+"-5000 and Latitude<"+
			latitude+"+5000 and Longitude>"+longitude+"-5000 and Longitude<"+longitude+"+5000";
		List<Object[]> lst = session.createSQLQuery(sql).list();
		for(Object[] tuple:lst){
			Photo photo = new Photo();
			photo.setId((String)tuple[0]);
			photo.setTags((String)tuple[1]);
			photo.setPath((String)tuple[2]);
			photo.setLatitude((String)tuple[3]);
			photo.setLongitude((String)tuple[4]);
			photo.setName((String)tuple[5]);
			photo.setSource((String)tuple[6]);
			photo.setOwnerId((Integer)tuple[7]);
			photo.setUploadDate((Date)tuple[8]);
			photos.add(photo);
		}
		return photos;
		
	}
}
