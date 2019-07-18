package com.ask.base.service.impl;
import com.ask.base.componet.config.FileConfig;
import com.ask.base.componet.util.MyFileUtils;
import com.ask.base.entity.FileInfoDetails;
import com.ask.base.service.ThumbnailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
@Slf4j
public class LocalThumbnailServiceImpl implements ThumbnailService {

	@Autowired
	private FileConfig fileConfig;

	private String l1;

	private String l2;

	private static final String[] imgExt = { "jpg", "png", "bmp" };

	@Override
	public void createThumbnail(FileInfoDetails fileInfoDetails) {
		String path = fileInfoDetails.getPath();
		String ext = MyFileUtils.getExt(path);
		if (ArrayUtils.contains(imgExt, ext)) {
			try (InputStream iStream = new FileInputStream(getPath(path));
					OutputStream oStream = getOutputStream(1, path);
					OutputStream oStream2 = getOutputStream(2, path)) {
				Image image = ImageIO.read(iStream);
				int o_w = image.getWidth(null);
				int o_h = image.getHeight(null);
				double scale = Math.max(o_w * 1.0 / 512, o_h * 1.0 / 512);
				double scale2 = Math.max(o_w * 1.0 / 256, o_h * 1.0 / 256);
				int n_w = Double.valueOf(o_w / scale).intValue();
				int n_h = Double.valueOf(o_h / scale).intValue();
				int n2_w = Double.valueOf(o_w / scale2).intValue();
				int n2_h = Double.valueOf(o_h / scale2).intValue();
				BufferedImage bi = new BufferedImage(n_w, n_h, BufferedImage.TYPE_INT_RGB);
				BufferedImage bi2 = new BufferedImage(n2_w, n2_h, BufferedImage.TYPE_INT_RGB);
				bi.getGraphics().drawImage(image, 0, 0, n_w, n_h, null);
				bi2.getGraphics().drawImage(image, 0, 0, n2_w, n2_h, null);
				ImageIO.write(bi, "jpg", oStream);
				ImageIO.write(bi2, "jpg", oStream2);
				String l1path =l1+path;
				l1path = l1path.substring(l1path.indexOf("\\th1")+4);
				fileInfoDetails.setThumbnailPath(l1path);
				String l1path2 =l2+path;
				l1path2 = l1path2.substring(l1path2.indexOf("\\th2")+4);
				fileInfoDetails.setThumbnailPath2(l1path2);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("创建缩略图失败");
			}
		}
	}

	@Override
	public InputStream getStream(String filename, int level) throws IOException {
		return new FileInputStream(getPath(filename, level));
	}

	@PostConstruct
	public void initService() throws Exception {
		String path = fileConfig.getPath();
		l1 = path + File.separator + "th1" + File.separator;
		l2 = path + File.separator + "th2" + File.separator;
		FileUtils.forceMkdir(new File(l1));
		FileUtils.forceMkdir(new File(l2));
	}

	private OutputStream getOutputStream(int level, String path) throws IOException {
		switch (level) {
		case 1:
			String tempPath = l1 + path;
			tempPath = tempPath.substring(0,tempPath.lastIndexOf(File.separator));
			File file =new File(tempPath);
			if (!file.exists()) {
				FileUtils.forceMkdir(file);
			}
			return new FileOutputStream(l1 + path);
		case 2:
			String tempPath2 = l2 + path;
			tempPath2 = tempPath2.substring(0,tempPath2.lastIndexOf(File.separator));
			File file2 =new File(tempPath2);
			if (!file2.exists()) {
				FileUtils.forceMkdir(file2);
			}
			return new FileOutputStream(l2 + path);
		}
		return null;
	}
	public String getRelativePath(String filename, UserDetails userDetails) throws Exception {
		String realPath = getPath(ObjectUtils.isEmpty(userDetails)
				?"public"+File.separator+filename
				:userDetails.getUsername()+File.separator+filename);
		return realPath.substring(realPath.indexOf(File.separator+"uploads"+File.separator)+9);
	}
	private String getPath(String filename) throws IOException {
		String path = fileConfig.getPath() + File.separator + filename;
		File file =new File(path);
		if (!file.exists()){
			FileUtils.forceMkdir(new File(path));
		}
		return path;
	}

	private String getPath(String filename, int level) {
		return fileConfig.getPath() + File.separator + "th" + level + File.separator + filename + ".jpg";
	}

}
