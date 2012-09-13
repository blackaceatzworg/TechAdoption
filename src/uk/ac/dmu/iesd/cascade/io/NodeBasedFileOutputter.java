package uk.ac.dmu.iesd.cascade.io;

import repast.simphony.data.logging.outputter.DelimitedFormatter;
import repast.simphony.data.logging.outputter.FileOutputter;
import repast.simphony.data.logging.outputter.Formatter;
import repast.simphony.data.logging.outputter.L4JFileOutputter;
import repast.simphony.data.logging.outputter.LMDelimitedFormatter;
import repast.simphony.data.logging.outputter.OutputterUtil;
import repast.simphony.data.logging.outputter.StaticOutputterFactory;
import repast.simphony.data.logging.outputter.engine.DefaultFileOutputterDescriptor;
import repast.simphony.data.logging.outputter.engine.DefaultOutputterDescriptor;
import repast.simphony.data.logging.outputter.engine.FileOutputterDescriptor;
import repast.simphony.essentials.RepastEssentials;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.SimpleFactory;

public class NodeBasedFileOutputter extends DefaultOutputterDescriptor<FileOutputter> implements
				FileOutputterDescriptor
{

	int randomSeed = -1;
	
	private String fileName;

	private boolean insertTimeToFileName;
	private boolean insertBatchToFileName;
	private boolean insertRunToFileName;

	private boolean appendToFile;

	private boolean writeHeader;

	private Formatter formatType;

	private String delimiter=",";
	private String formattingString;

	public NodeBasedFileOutputter() {
		this(null,DelimitedFormatter.DEFAULT_DELIMITER);
	}
	
	public NodeBasedFileOutputter(String delimiter) {
		this(null,delimiter);
	}

	public NodeBasedFileOutputter(String name,String delimiter) {
		super(name);
		this.insertBatchToFileName = true;
		this.insertRunToFileName = true;
		this.insertTimeToFileName = true;
		this.appendToFile = false;
		this.writeHeader = true;
		this.delimiter = delimiter;
		this.formatType = new LMDelimitedFormatter(delimiter);

		super.setOutputterFactory(new SimpleFactory<FileOutputter>() {
			public FileOutputter create() {
				return createFileOutputter();
			}
		});
	}

	private FileOutputter createFileOutputter() {
		String newFileName = fileName;
		String nodeName = System.getenv("PBS_JOBID");
		if (nodeName != null)
		{
		newFileName = OutputterUtil.insertVarToString(newFileName, nodeName, nodeName);
		}
		if (insertTimeToFileName)
			newFileName = OutputterUtil.insertVarToString(newFileName, 
					FileOutputter.TIME_VAR_NAME, FileOutputter.TIME_VAR);
		if (insertBatchToFileName)
			newFileName = OutputterUtil.insertVarToString(newFileName,
					FileOutputter.BATCH_NUMBER_VAR_NAME, FileOutputter.BATCH_NUMBER_VAR);
		if (insertRunToFileName)
			newFileName = OutputterUtil.insertVarToString(newFileName,
					FileOutputter.RUN_NUMBER_VAR_NAME, FileOutputter.RUN_NUMBER_VAR);

		FileOutputter outputter = new L4JFileOutputter(newFileName,delimiter);
		outputter.setAppend(appendToFile);
		StaticOutputterFactory.initPrintOutputter(outputter, getColumns(), formatType);
		return outputter;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#getAppendToFile()
	 */
	public boolean getAppendToFile() {
		return appendToFile;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#setAppendToFile(boolean)
	 */
	public void setAppendToFile(boolean appendToFile) {
		this.appendToFile = appendToFile;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#getDelimiter()
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#setDelimiter(java.lang.String)
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#getFileName()
	 */
	public String getFileName() {
		return fileName;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#setFileName(java.lang.String)
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#getFormattingString()
	 */
	public String getFormattingString() {
		return formattingString;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#setFormattingString(java.lang.String)
	 */
	public void setFormattingString(String formattingString) {
		this.formattingString = formattingString;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#getFormatType()
	 */
	public Formatter getFormatter() {
		return formatType;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#setFormatType(repast.simphony.data.logging.output.OutputFormatType)
	 */
	public void setFormatter(Formatter formatType) {
		this.formatType = formatType;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#getInsertTimeToFileName()
	 */
	public boolean getInsertTimeToFileName() {
		return insertTimeToFileName;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#setInsertTimeToFileName(boolean)
	 */
	public void setInsertTimeToFileName(boolean insertTimeToFileName) {
		this.insertTimeToFileName = insertTimeToFileName;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#getWriteHeader()
	 */
	public boolean getWriteHeader() {
		return writeHeader;
	}

	/* (non-Javadoc)
	 * @see repast.simphony.engine.controller.descriptor.FileOutputterDescriptor#setWriteHeader(boolean)
	 */
	public void setWriteHeader(boolean writeHeader) {
		this.writeHeader = writeHeader;
	}

	public void setInsertBatchToFileName(boolean insertBatchToFileName) {
		this.insertBatchToFileName = insertBatchToFileName;
	}

	public void setInsertRunToFileName(boolean insertRunToFileName) {
		this.insertRunToFileName = insertRunToFileName;
	}

	public boolean isInsertBatchToFileName() {
		return insertBatchToFileName;
	}

	public boolean isInsertRunToFileName() {
		return insertRunToFileName;
	}
	
}
