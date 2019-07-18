package com.ask.base.service.impl;
import com.ask.base.componet.config.FileConfig;
import com.ask.base.entity.FileInfoDetails;
import com.ask.base.service.FileStorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.*;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService {

	@Autowired
	private FileConfig config;

	@Override
	public boolean save(FileInfoDetails fileInfoDetails, InputStream inputStream) throws IOException {
		FileUtils.copyToFile(inputStream, new File(getPath(fileInfoDetails.getPath())));
		return true;
	}

	@Override
	public boolean save(FileInfoDetails fileInfoDetails, File file) throws IOException {
		FileUtils.copyFile(file, new File(getPath(fileInfoDetails.getPath())));
		return true;
	}

	@Override
	public boolean delete(String path) throws IOException {
		return FileUtils.deleteQuietly(new File(getPath(path)));
	}

	@Override
	public boolean exist(String path) throws Exception {
		return new File(getPath(path)).exists();
	}

	@Override
	public InputStream getInputStream(String path) throws Exception {
		return new FileInputStream(getPath(path));
	}
	@Override
	public String getRelativePath(String filename, UserDetails userDetails) throws Exception {
		String realPath = getPath(ObjectUtils.isEmpty(userDetails)
							?"public"+File.separator+filename
							:userDetails.getUsername()+File.separator+filename);
		return realPath.substring(realPath.indexOf(File.separator+"uploads"+File.separator)+9);
	}
	private String getPath(String filename) throws IOException {
		String path = config.getPath() + File.separator + filename.substring(0,filename.lastIndexOf(File.separator));
		 File file =new File(path);
		if (!file.exists()){
			FileUtils.forceMkdir(new File(path));
		}
		return config.getPath() + File.separator + filename;
	}

	@Override
	public boolean save(String path, File[] src) throws IOException {
		File target = new File(getPath(path));
		try (OutputStream out = FileUtils.openOutputStream(target, true)) {
			for (File file : src) {
				FileUtils.copyFile(file, out);
			}
		}
		return true;
	}

	/**
	 * 初始化的时候，创建目录
	 */
	@PostConstruct
	public void initStorage() throws Exception {
		String path = config.getPath();
		File file = new File(path);
		FileUtils.forceMkdir(file);
	}
}
