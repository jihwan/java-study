package kosta.java.io.storage.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import kosta.java.io.storage.BaseStorage;
import kosta.java.io.storage.config.StorageConfig;
import kosta.java.io.storage.data.ParamData;
import kosta.java.io.storage.data.ResultData;

public class DelimiterTextStreamStorage extends BaseStorage {

	private static final String DE = ",";

	public DelimiterTextStreamStorage(String storageName) {
		super(storageName);
	}

	@Override
	public void insert(ParamData data) {

		try (FileOutputStream fos = new FileOutputStream(this.storageFile, true);) {
			insert(fos, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertMany(List<ParamData> datas) {

		try (FileOutputStream fos = new FileOutputStream(this.storageFile, true);) {
			for (ParamData data : datas) {
				insert(fos, data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insert(OutputStream fos, ParamData data) throws IOException {
		for (String value : data.getDataAsString()) {
			fos.write(value.getBytes());
			fos.write(DE.getBytes());
		}
		fos.write(LS.getBytes());
	}

	@Override
	public ResultData select(String key) {

		ResultData result = new ResultData();

		try (FileInputStream fis = new FileInputStream(this.storageFile)) {

			byte[] buffer = new byte[16];
			int readSize;

			StringBuilder bufferBuilder = new StringBuilder();

			while ((readSize = fis.read(buffer)) != -1) {

				ResultData tempData = select(buffer, readSize, bufferBuilder, 0, key);

				if (tempData != null) {
					result = tempData;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ResultData> selectAll() {

		List<ResultData> results = new ArrayList<>();
		
		try ( BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.storageFile));
					ByteArrayOutputStream bas = new ByteArrayOutputStream();
				) {

			byte[] buffer = new byte[16];
			int readSize;

			StringBuilder bufferBuilder = new StringBuilder();

			while ((readSize = bis.read(buffer)) != -1) {
				bas.write(buffer, 0, readSize);
			}
			
			
			String string = bas.toString( StorageConfig.INSTANCE.charset().name() );
			StringTokenizer stringTokenizer = new StringTokenizer(string, LS);
			while (stringTokenizer.hasMoreTokens()) {
				String line = stringTokenizer.nextToken();
				
				String[] colums = line.split(DE);
				ResultData resultData = new ResultData();
				
				for (String col : colums) {
					resultData.add(col);
				}
				
				results.add(resultData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public List<ResultData> selectAll2() {

		List<ResultData> results = new ArrayList<>();
		
		try (FileInputStream fis = new FileInputStream(this.storageFile)) {

			byte[] buffer = new byte[16];
			int readSize;

			StringBuilder bufferBuilder = new StringBuilder();

			while ((readSize = fis.read(buffer)) != -1) {

				ResultData tempData = select(buffer, readSize, bufferBuilder);

				if (tempData != null) {
					results.add(tempData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<ResultData> selectBy(int columnIndex, String value) {

		List<ResultData> results = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(this.storageFile)) {

			byte[] buffer = new byte[16];
			int readSize;

			StringBuilder bufferBuilder = new StringBuilder();

			while ((readSize = fis.read(buffer)) != -1) {

				ResultData tempData = select(buffer, readSize, bufferBuilder, columnIndex, value);

				if (tempData != null) {
					results.add(tempData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

	private ResultData select(byte[] buffer, int readSize, StringBuilder bufferBuilder, int conditionColumn,
			String conditionValue) {

		ResultData result = null;

		// 실제 파일로부터 읽은 bytes를 문자열로 변환
		String bufferStr = new String(Arrays.copyOfRange(buffer, 0, readSize), StorageConfig.INSTANCE.charset());
		bufferBuilder.append(bufferStr);

		if (!bufferBuilder.toString().contains(LS)) {
			return null;
		}

		// 읽어들인 버퍼에 LS가 있으면 라인(Row) 분석
		String[] lines = bufferBuilder.toString().split(LS);
		String currentLine = null;

		for (int lineIndex = 0; lineIndex < lines.length - 1; lineIndex++) {
			currentLine = lines[lineIndex];

			String[] columns = currentLine.split(DE);
			if (!columns[conditionColumn].equals(conditionValue)) {
				continue;
			}

			result = new ResultData();

			// 결과객체 생성
			for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
				result.add(columns[columnIndex]);
			}
		}

		// buferBuilder를 비운 후 버퍼에서 읽어들이던 라인 조각을 넣음.
		bufferBuilder.setLength(0);
		bufferBuilder.append(lines[lines.length - 1]);

		return result;
	}
	
	private ResultData select(byte[] buffer, int readSize, StringBuilder bufferBuilder) {

		ResultData result = null;

		// 실제 파일로부터 읽은 bytes를 문자열로 변환
		String bufferStr = new String(Arrays.copyOfRange(buffer, 0, readSize), StorageConfig.INSTANCE.charset());
		bufferBuilder.append(bufferStr);

		if (!bufferBuilder.toString().contains(LS)) {
			return null;
		}

		// 읽어들인 버퍼에 LS가 있으면 라인(Row) 분석
		String[] lines = bufferBuilder.toString().split(LS);
		String currentLine = null;

		for (int lineIndex = 0; lineIndex < lines.length - 1; lineIndex++) {
			currentLine = lines[lineIndex];

			String[] columns = currentLine.split(DE);


			result = new ResultData();

			// 결과객체 생성
			for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
				result.add(columns[columnIndex]);
			}
		}

		// buferBuilder를 비운 후 버퍼에서 읽어들이던 라인 조각을 넣음.
		bufferBuilder.setLength(0);
		bufferBuilder.append(lines[lines.length - 1]);

		return result;
	}

	@Override
	public void update(String key, ParamData data) {
		throw new RuntimeException("Not yet implementation.");
	}

	@Override
	public void delete(String key) {
		throw new RuntimeException("Not yet implementation.");
	}

}
