package com.ask.base.entity;

public interface FileInfoDetails {

	 String getId();

	 String getFilename();

	 void   setFilename(String filename);

	 String getPath();

	 void   setPath(String path);

	 Long getSize();

	 void setSize(Long path);

	String getPreviewPath();

	void setPreviewPath(String previewPath);

	String getThumbnailPath();

	void setThumbnailPath(String previewPath1);

	String getThumbnailPath2();

	void setThumbnailPath2(String previewPath2);
}
