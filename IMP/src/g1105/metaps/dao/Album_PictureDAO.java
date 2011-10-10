package g1105.metaps.dao;

import g1105.metaps.entity.Album_picture;
import g1105.metaps.key.Album_picturePK;
import g1105.ps.dao.ParentDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class Album_PictureDAO extends ParentDAO{
	public Album_PictureDAO(HibernateTemplate template) {
		super(template);
	}
	public void addPictureToAlbum(Album_picture albumPicture) {
		template.save(albumPicture);
	}
	public void deletePictureFromAlbum(Album_picture albumPicture) {
		template.delete(albumPicture);
	}
	public List<Album_picture> getAlbumPictures(Album_picture albumPicture) {
//		return template.findByExample(albumPicture);
		List<Album_picture> albumPictures = new ArrayList<Album_picture>();
		Session session = template.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		List pictureIds = session.createSQLQuery("select pictureId from album_picture where albumId=" +
				albumPicture.getAlbumPicturePK().getAlbumId()).list();
		for(Object pictureId: pictureIds){
			Album_picture albumPicture2 = new Album_picture();
			Album_picturePK albumPicturePK = new Album_picturePK();
			albumPicturePK.setAlbumId(albumPicture.getAlbumPicturePK().getAlbumId());
			albumPicturePK.setPictureId((String)pictureId);
			albumPicture2.setAlbumPicturePK(albumPicturePK);
			albumPictures.add(albumPicture2);
		}
		return albumPictures;
	}
}
