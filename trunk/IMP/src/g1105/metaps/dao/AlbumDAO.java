package g1105.metaps.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import g1105.metaps.entity.Album;
import g1105.ps.dao.ParentDAO;

public class AlbumDAO extends ParentDAO{

	public AlbumDAO(HibernateTemplate template) {
		super(template);
	}
	public void addAlbum(Album album) {
		template.save(album);
	}
	public void deleteAlbum(Album album) {
		template.delete(album);
	}
	public List<Album> getAlbum(Album album) {
		return template.findByExample(album);
	}
	public List<Album> getUserAllAlbums(Integer userId) {
		Album album = new Album();
		album.setUserId(userId);
		List<Album> albums = template.findByExample(album);
		return albums;
	}
	public Album getAlbumById(Integer id) {
		return (Album)template.get(Album.class, id);
	}
}
