//  Copyright (c) 2005, Regents of the University of California
//  All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions are
//  met:
//
//    * Redistributions of source code must retain the above copyright notice,
//  this list of conditions and the following disclaimer.
//    * Redistributions in binary form must reproduce the above copyright
//  notice, this list of conditions and the following disclaimer in the
//  documentation and/or other materials provided with the distribution.
//    * Neither the name of the University of California, San Diego (UCSD) nor
//  the names of its contributors may be used to endorse or promote products
//  derived from this software without specific prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
//  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
//  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
//  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
//  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
//  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
//  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
//  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
//  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
//  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
//  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//
//
//  FILE
//  SRBException.java  -  edu.sdsc.grid.io.srb.SRBException
//
//  CLASS HIERARCHY
//  java.lang.Object
//      |
//      +-java.io.IOException
//          |
//          +-edu.sdsc.grid.io.srb.srbException
//
//  PRINCIPAL AUTHOR
//  Lucas Gilbert, SDSC/UCSD
//
//
package edu.sdsc.grid.io.srb;

import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * This class encompasses the legacy errors that can be returned by the SRB
 * server. When an exception occurs in the SRB server a negative integer will be
 * returned by the server which corresponds to a certain error message. This
 * class provides methods to read that value and/or look up the related error.
 */
public class SRBException extends IOException {
	/**
	 * The list of errors.
	 */
	// Properties allows a default value
	static Properties srbException = new Properties();

	/**
	 * The error number
	 */
	int exceptionType;

	/**
	 * Construct a SRBException. This constructor extracts the error type from
	 * the message string. The first line of the error message will generally
	 * have a negative integer at the end which is the SRB exception type.
	 */
	public SRBException(final String message) {
		super(message);

		StringTokenizer token = new StringTokenizer(message, "\n");
		String temp = token.nextToken();

		try {
			exceptionType = Integer.parseInt(temp.substring(temp.length() - 5));
		} catch (Throwable e) {
			exceptionType = -1;
		}

		if (exceptionType >= 0) {
			exceptionType = -1;
		}
	}

	/**
	 * Construct an SRBException.
	 */
	public SRBException(final String message, final int srbExceptionType) {
		super(message);

		if (srbExceptionType < 0) {
			exceptionType = srbExceptionType;
		} else {
			exceptionType = -1;
		}
	}

	/**
	 * Construct an SRBException with an empty message of the specified type.
	 */
	public SRBException(final int srbExceptionType) {
		super("");

		if (srbExceptionType < 0) {
			exceptionType = srbExceptionType;
		} else {
			throw new RuntimeException(
					"Unknown SRB error type returned from server."
							+ exceptionType);
		}
	}

	/**
	 * @return the SRB server error type.
	 */
	public int getType() {
		return exceptionType;
	}

	/**
	 * @return the standard SRB server error message of this error type.
	 */
	public String getStandardMessage() {
		return srbException.getProperty(String.valueOf(exceptionType),
				"Unknown SRB error returned from server." + exceptionType);
	}

	/**
	 * Get the standard SRB error message for any error type.
	 */
	public static String getStandardMessage(final int srbExceptionType) {
		if (srbExceptionType < 0) {
			return srbException.getProperty(String.valueOf(srbExceptionType),
					"Unknown SRB error type requested");
		} else {
			throw new IllegalArgumentException(
					"Unknown SRB error type requested" + srbExceptionType);
		}
	}

	/**
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return super.toString() + " " + getStandardMessage() + " " + getType();
	}

	static {
		srbException.setProperty("-1",
				"Unknown SRB error type returned from server"); // added to java
																// to cover
																// parse errors
		// MAGIC_LINE (DONT CHANGE THIS LINE - it has to come above the
		// following static declarations);
		/* ERROR TABLE DECLARATIONS */
		srbException.setProperty("0", "STATUS_OK");
		srbException.setProperty("1", "STATUS_FOUND");
		srbException
				.setProperty("-1000", "START_OF_ERR_CODE START_OF_ERR_CODE");
		srbException.setProperty("-1001",
				"AUTH_ERR_USERNAME NULL or bad userName");
		srbException.setProperty("-1002",
				"AUTH_ERR_HOST_BASE_AUTH hostBase auth failed");
		srbException
				.setProperty("-1003", "AUTH_ERR_MDAS_AUTH MDAS auth failed");
		srbException.setProperty("-1004", "AUTH_ERR_AUTH auth failed");
		srbException.setProperty("-1005", "AUTH_ERR_SEA_AUTH SEA auth failed");
		srbException.setProperty("-1006",
				"AUTH_ERR_SEA_ENC SEA encryption failed");
		srbException.setProperty("-1007",
				"AUTH_ERR_PROXY_NOPRIV proxy user not privileged");
		srbException.setProperty("-1008",
				"ILLEGAL_OPR_TICKET_USER illegal operation for a ticket user");
		srbException.setProperty("-1009",
				"AUTH_ERR_SEA_NO_SUPPORT SEA not supported");
		srbException.setProperty("-1010",
				"AUTH_ERR_MDAS_AUTH_NO_SUPPORT MDAS_AUTH not supported");
		srbException
				.setProperty("-1011",
						"AUTH_ERR_GSI_SETUP AUTH_ERR_GSI_SETUP Error in setting up GSI auth");
		srbException.setProperty("-1012",
				"AUTH_ERR_GSI_AUTH AUTH_ERR_GSI_AUTH GSI auth error");
		srbException
				.setProperty(
						"-1013",
						"AUTH_ERR_NO_GSI_SERVER_DN AUTH_ERR_NO_GSI_SERVER_DN Unable to get the server's Dn");
		srbException
				.setProperty(
						"-1014",
						"AUTH_ERR_NO_GSI_DN_IN_MCAT AUTH_ERR_NO_GSI_DN_IN_MCAT query user's dn in MCAT returns nothing");
		srbException
				.setProperty(
						"-1015",
						"AUTH_ERR_GSI_DN_MISMATCH AUTH_ERR_GSI_DN_MISMATCH Mismatch in Dn between GSI and MCAT");
		srbException.setProperty("-1016",
				"AUTH_ERR_GSI_NO_SUPPORT GSI not supported");
		srbException.setProperty("-1017",
				"AUTH_ERR_ENCRYPT_AUTH Encrypt auth failed");
		srbException.setProperty("-1018", "GET_PW_UID_ERROR getpwuid error");
		srbException
				.setProperty("-1019",
						"SRB_SECURE_COMM_NO_SUPPORT SRB Secure Communications not supported");
		srbException
				.setProperty("-1020",
						"SRB_SECURE_COMM_NOT_ESTABLISHED SRB Secure Communications not established");
		srbException
				.setProperty("-1021",
						"SRB_SECURE_COMM_LIBRARY_ERROR SRB Secure Communications Library Error");
		srbException
				.setProperty(
						"-1022",
						"AUTH_ERR_CLIENT_SVR_DIFF_ZONE The client and the server users are from different zone");
		srbException
				.setProperty(
						"-1023",
						"SVR_TO_SVR_CONNECT_ERROR Problem with a server connecting to a remote SRB master. The remote SRB master may be down");
		srbException
				.setProperty("-1024",
						"CLIENT_AUTH_REQUIRED Doing a cross-zone operation. Client auth is required");
		srbException
				.setProperty(
						"-1025",
						"CLIENT_CROSS_ZONE_AUTH_FAILED cross-zone auth required delegate client auth. This authentication step failed");
		srbException.setProperty("-1100",
				"CLI_ERR_COMMAND client command failed");
		srbException.setProperty("-1101",
				"CLI_ERR_RETURN_LEN wrong returned lenghth");
		srbException.setProperty("-1102", "CLI_ERR_INVAILD_USER Invalid user");
		srbException.setProperty("-1103", "CLI_ERR_HOST_NAME Bad host name");
		srbException.setProperty("-1104",
				"CLI_ERR_SOCKET Unable to open socket");
		srbException.setProperty("-1105",
				"CLI_ERR_SOCKETNAME getsockname error");
		srbException.setProperty("-1106",
				"CLI_ERR_SEND_PACKET PacketSend error");
		srbException.setProperty("-1107", "CLI_ERR_RECV recv error");
		srbException.setProperty("-1108", "CLI_ERR_FDOPEN socket fdopen error");
		srbException.setProperty("-1109",
				"CLI_ERR_SOCK_CONN socket connect error");
		srbException.setProperty("-1110",
				"CLI_NO_ANSWER The query has no answer");
		srbException.setProperty("-1111",
				"CLI_ERR_MALLOC Client library malloc error");
		srbException.setProperty("-1112",
				"CLI_ERR_INVAILD_DOMAIN Invalid domain name");
		srbException.setProperty("-1113",
				"CLI_ERR_BROKEN_PIPE broken pipe to server");
		srbException.setProperty("-1114",
				"CLI_FATAL_ERROR FATAL error msg from server");
		srbException.setProperty("-1115",
				"CLI_ERR_COLLNAME The input collection is incorrect");
		srbException.setProperty("-1116",
				"CLI_ERR_NAME2LONG The input collection/dataname is too long");
		srbException
				.setProperty("-1117",
						"CLI_WRITE_LEN_ERROR write error. length returned not same as input");
		srbException.setProperty("-1118",
				"CLI_INCOMPATIBLE_VERSION client|svr version incompatible");
		srbException
				.setProperty("-1119",
						"CLI_FUNC_NOT_SUPPORTED The function call is not supported by the server");
		srbException
				.setProperty("-1120",
						"CLI_NUM_ARG_MISMATCH The num of arg does not match the configuration");
		srbException
				.setProperty("-1121",
						"CLI_AUTH_SCHEME_NOT_SUPPORTED The authentication scheme is not supported");
		srbException.setProperty("-1122",
				"CLI_BAD_AUTH_OPTION The authentication option string is bad");
		srbException.setProperty("-1123",
				"CLI_NO_LOCAL_HOME env HOME is not set");
		srbException
				.setProperty("-1124",
						"CLI_TAPE_RESC_LOC_ERR Location of tape resource on different host");
		srbException
				.setProperty("-1125",
						"CLI_ENV_FILE_ERR Problem with the .MdasEnv and/or .MdasAuth files");
		srbException.setProperty("-1200",
				"UTIL_ERROR_ENV_FILE_OPEN Unable to open .MdasEnv file");
		srbException.setProperty("-1201",
				"UTIL_ERROR_AUTH_FILE_OPEN Unable to open .MdasAuth file");
		srbException.setProperty("-1202",
				"UTIL_ERROR_DEFAULT_RSRC_NOT_FOUND No default Resource");
		srbException.setProperty("-1203",
				"UTIL_ERROR_DEF_RSRC_FILE_OPEN Unable to open .MdasEnv file");
		srbException
				.setProperty("-1204",
						"UTIL_ERROR_MAX_RESULT_LIMIT_EXCEEDED Query returns too many rows");
		srbException.setProperty("-1205",
				"MAX_OPEN_SRBIO_EXCEEDED MAX_OPEN_SRBIO_EXCEEDED");
		srbException.setProperty("-1206",
				"SRB_OBJ_WRITE_ERROR SRB_OBJ_WRITE_ERROR");
		srbException.setProperty("-1207",
				"SRB_OBJ_READ_ERROR SRB_OBJ_READ_ERROR");
		srbException.setProperty("-1208",
				"SRB_OBJ_SEEK_ERROR SRB_OBJ_SEEK_ERROR");
		srbException.setProperty("-1209",
				"SRBIO_MEMORY_ALLOCATION_ERROR SRBIO_MEMORY_ALLOCATION_ERROR");
		srbException
				.setProperty("-1210", "SRBIO_FATAL_ERROR SRBIO_FATAL_ERROR");
		srbException.setProperty("-1211",
				"SRBIO_FILL_DIRTY_ERROR SRBIO_FILL_DIRTY_ERROR");
		srbException
				.setProperty("-1212",
						"UTIL_ERROR_TARGET_ALREADY_EXIST UTIL_ERROR_TARGET_ALREADY_EXIST");
		srbException.setProperty("-1300",
				"UNIX_UNKNOWN_ERR Unknown UNIX error.");
		srbException.setProperty("-1301",
				"UNIX_EPERM UNIX error. Not super-user");
		srbException.setProperty("-1302",
				"UNIX_ENOENT UNIX error. No such file or directory");
		srbException.setProperty("-1303",
				"UNIX_ESRCH UNIX error. No such process");
		srbException.setProperty("-1304",
				"UNIX_EINTR UNIX error. Interrupted system call");
		srbException.setProperty("-1305", "UNIX_EIO UNIX error. I/O error");
		srbException.setProperty("-1306",
				"UNIX_ENXIO UNIX error. No such device or address");
		srbException.setProperty("-1307",
				"UNIX_E2BIG UNIX error. Arg list too long");
		srbException.setProperty("-1308",
				"UNIX_ENOEXEC UNIX error. Exec format error");
		srbException.setProperty("-1309",
				"UNIX_EBADF UNIX error. Bad file number");
		srbException
				.setProperty("-1310", "UNIX_ECHILD UNIX error. No children");
		srbException.setProperty("-1311",
				"UNIX_EAGAIN UNIX error. Resource temporarily unavailable");
		srbException.setProperty("-1312",
				"UNIX_ENOMEM UNIX error. Not enough core");
		srbException.setProperty("-1313",
				"UNIX_EACCES UNIX error. Permission denied");
		srbException
				.setProperty("-1314", "UNIX_EFAULT UNIX error. Bad address");
		srbException.setProperty("-1315",
				"UNIX_ENOTBLK UNIX error. Block device required");
		srbException.setProperty("-1316",
				"UNIX_EBUSY UNIX error. Mount device busy");
		srbException
				.setProperty("-1317", "UNIX_EEXIST UNIX error. File exists");
		srbException.setProperty("-1318",
				"UNIX_EXDEV UNIX error. Cross-device link");
		srbException.setProperty("-1319",
				"UNIX_ENODEV UNIX error. No such device");
		srbException.setProperty("-1320",
				"UNIX_ENOTDIR UNIX error. Not a directory");
		srbException.setProperty("-1321",
				"UNIX_EISDIR UNIX error. Is a directory");
		srbException.setProperty("-1322",
				"UNIX_EINVAL UNIX error. Invalid argument");
		srbException.setProperty("-1323",
				"UNIX_ENFILE UNIX error. File table overflow");
		srbException.setProperty("-1324",
				"UNIX_EMFILE UNIX error. Too many open files");
		srbException.setProperty("-1325",
				"UNIX_ENOTTY UNIX error. Inappropriate ioctl for device");
		srbException.setProperty("-1326",
				"UNIX_ETXTBSY UNIX error. Text file busy");
		srbException.setProperty("-1327",
				"UNIX_EFBIG UNIX error. File too large");
		srbException.setProperty("-1328",
				"UNIX_ENOSPC UNIX error. No space left on device");
		srbException.setProperty("-1329",
				"UNIX_ESPIPE UNIX error. Illegal seek");
		srbException.setProperty("-1330",
				"UNIX_EROFS UNIX error. Read only file system");
		srbException.setProperty("-1331",
				"UNIX_EMLINK UNIX error. Too many links");
		srbException.setProperty("-1332", "UNIX_EPIPE UNIX error. Broken pipe");
		srbException.setProperty("-1333",
				"UNIX_EDOM UNIX error. Math arg out of domain of func");
		srbException.setProperty("-1334",
				"UNIX_ERANGE UNIX error. Math result not representable");
		srbException.setProperty("-1335",
				"UNIX_ENOMSG UNIX error. No message of desired type");
		srbException.setProperty("-1336",
				"UNIX_EIDRM UNIX error. Identifier removed");
		srbException.setProperty("-1337",
				"UNIX_ECHRNG UNIX error. Channel number out of range");
		srbException.setProperty("-1338",
				"UNIX_EL2NSYNC UNIX error. Level 2 not synchronized");
		srbException.setProperty("-1339",
				"UNIX_EL3HLT UNIX error. Level 3 halted");
		srbException.setProperty("-1340",
				"UNIX_EL3RST UNIX error. Level 3 reset");
		srbException.setProperty("-1341",
				"UNIX_ELNRNG UNIX error. Link number out of range");
		srbException.setProperty("-1342",
				"UNIX_EUNATCH UNIX error. Protocol driver not attached");
		srbException.setProperty("-1343",
				"UNIX_ENOCSI UNIX error. No CSI structure available");
		srbException.setProperty("-1344",
				"UNIX_EL2HLT UNIX error. Level 2 halted");
		srbException.setProperty("-1345",
				"UNIX_EDEADLK UNIX error. Deadlock condition");
		srbException.setProperty("-1346",
				"UNIX_ENOLCK UNIX error. No record locks available");
		srbException.setProperty("-1347",
				"UNIX_ECANCELED UNIX error. Operation canceled");
		srbException.setProperty("-1348",
				"UNIX_ENOTSUP UNIX error. Operation not supported");
		srbException.setProperty("-1349",
				"UNIX_EDQUOT UNIX error. Disc quota exceeded");
		srbException.setProperty("-1350",
				"UNIX_EBADE UNIX error. Invalid exchange");
		srbException.setProperty("-1351",
				"UNIX_EBADR UNIX error. Invalid request descriptor");
		srbException.setProperty("-1352",
				"UNIX_EXFULL UNIX error. Exchange full");
		srbException.setProperty("-1353", "UNIX_ENOANO UNIX error. No anode");
		srbException.setProperty("-1354",
				"UNIX_EBADRQC UNIX error. Invalid request code");
		srbException.setProperty("-1355",
				"UNIX_EBADSLT UNIX error. Invalid slot");
		srbException.setProperty("-1356",
				"UNIX_EDEADLOCK UNIX error. File locking deadlock error");
		srbException
				.setProperty("-1400", "HPSS_UNKNOWN_ERR Unknown HPSS error");
		srbException.setProperty("-1401",
				"HPSS_SRB_EPERM HPSS error. Operation not permitted");
		srbException.setProperty("-1402",
				"HPSS_SRB_ENOENT HPSS error. No such file or directory");
		srbException.setProperty("-1405", "HPSS_SRB_EIO HPSS error. I/O error");
		srbException.setProperty("-1409",
				"HPSS_SRB_EBADF HPSS error. Bad file descriptor");
		srbException.setProperty("-1411",
				"HPSS_SRB_EAGAIN HPSS error. Resource temporarily unavailable");
		srbException.setProperty("-1412",
				"HPSS_SRB_ENOMEM HPSS error. Not enough space");
		srbException.setProperty("-1413",
				"HPSS_SRB_EACCES HPSS error. Permission denied");
		srbException.setProperty("-1414",
				"HPSS_SRB_EFAULT HPSS error. Bad address");
		srbException.setProperty("-1416",
				"HPSS_SRB_EBUSY HPSS error. Resource busy ");
		srbException.setProperty("-1417",
				"HPSS_SRB_EEXIST HPSS error. File exists");
		srbException.setProperty("-1419",
				"HPSS_SRB_ENODEV HPSS error. No such device");
		srbException.setProperty("-1420",
				"HPSS_SRB_ENOTDIR HPSS error. Not a directory");
		srbException.setProperty("-1421",
				"HPSS_SRB_EISDIR HPSS error. Is a directory");
		srbException.setProperty("-1422",
				"HPSS_SRB_EINVAL HPSS error. Invalid argument");
		srbException.setProperty("-1424",
				"HPSS_SRB_EMFILE HPSS error. Too many open files");
		srbException.setProperty("-1428",
				"HPSS_SRB_ENOSPACE HPSS error. No space left on device");
		srbException.setProperty("-1431",
				"HPSS_SRB_EMLINK HPSS error. Too many links");
		srbException.setProperty("-1433",
				"HPSS_SRB_EDOM HPSS error. Domain error within math function");
		srbException.setProperty("-1434",
				"HPSS_SRB_ERANGE HPSS error. Result too large");
		srbException.setProperty("-1447",
				"HPSS_SRB_EWRPROTEC HPSS error. Write-protected media");
		srbException.setProperty("-1450",
				"HPSS_SRB_ECONN HPSS error. No connection");
		srbException.setProperty("-1452",
				"HPSS_SRB_ESTALE HPSS error. No filesystem");
		srbException.setProperty("-1459",
				"HPSS_SRB_EMSGSIZE HPSS error. Message too long");
		srbException
				.setProperty("-1464",
						"HPSS_SRB_EOPNOTSUPP HPSS error. Operation not supported on socket");
		srbException.setProperty("-1478",
				"HPSS_SRB_ETIMEDOUT HPSS error. Connection timed out");
		srbException.setProperty("-1486",
				"HPSS_SRB_ENAMETOOLONG HPSS error. File name too long");
		srbException.setProperty("-1487",
				"HPSS_SRB_ENOTEMPTY HPSS error. Directory not empty");
		srbException.setProperty("-1499",
				"HPSS_SRB_NOCOS HPSS error. Cannot assign a COS");
		srbException
				.setProperty(
						"-1500",
						"HPSS_SRB_TIMEOUT_OPEN1 HPSS timeout error. Cannot finish HPSS hpss_Open() operation in the alloted time HPSS timeout error. Cannot finish HPSS hpss_Open() operation in alloted time, called in paraHPSStoFAPCopy(), (parallel HPSS code)");
		srbException
				.setProperty(
						"-1501",
						"HPSS_SRB_TIMEOUT_OPEN2 HPSS timeout error. Cannot finish HPSS hpss_Open() operation in the alloted time HPSS timeout error. Cannot finish HPSS hpss_Open() operation in alloted time, called in paraFAPtoHPSSCopy(), (parallel HPSS code)");
		srbException
				.setProperty(
						"-1502",
						"HPSS_SRB_TIMEOUT_OPEN3 HPSS timeout error. Cannot finish HPSS hpssOpen() operation in alloted time HPSS timeout error. Cannot finish HPSS hpssOpen() operation in alloted time");
		srbException
				.setProperty(
						"-1503",
						"HPSS_SRB_TIMEOUT_READ HPSS timeout error. Cannot finish HPSS hpssRead hpssCreate() operation in alloted time");
		srbException
				.setProperty(
						"-1504",
						"HPSS_SRB_TIMEOUT_STAGE HPSS timeout error. Cannot finish HPSS hpssStage() operation in alloted time");
		srbException
				.setProperty(
						"-1505",
						"HPSS_SRB_TIMEOUT_CREATE HPSS timeout error. Cannot finish HPSS hpssCreate() operation in alloted time");
		srbException
				.setProperty(
						"-1600",
						"SQL_RSLT_TOO_LONG The requested SQL result is too long The requested SQL result is too long( > SQL_RESULT_LEN-2,000,000)");
		srbException.setProperty("-1700",
				"HTTP_ERR_BAD_PATHNAME Bat HTTP path name");
		srbException.setProperty("-1800",
				"PARA_OPR_NOT_HPSS_FT The file type must be HPSS file type");
		srbException
				.setProperty("-1801",
						"PARA_OPR_HPSS_NOT_SUPPORTED HPSS not enabled for HPSS operations");
		srbException.setProperty("-1802",
				"PARA_OPR_HPSS_READLIST_ERR hpss_ReadList error");
		srbException.setProperty("-1803",
				"PARA_OPR_HPSS_WRITELIST_ERR hpss_WriteList error");
		srbException.setProperty("-2001", "STATUS_ERROR");
		srbException.setProperty("-2003", "STATUS_INVALID");
		srbException.setProperty("-2006", "STATUS_NOT_DONE");
		srbException.setProperty("-2007", "STATUS_BAD_PACKET");
		srbException.setProperty("-2008", "SRB_EPERM Permission Error");
		srbException.setProperty("-2100",
				"INP_ERR_RES_FORMAT resource format (hostaddr:port error");
		srbException.setProperty("-2101",
				"INP_ERR_HOST_ADDR host addr not in config");
		srbException.setProperty("-2102",
				"INP_ERR_DB_TYPE DB type not supported");
		srbException.setProperty("-2103",
				"INP_ERR_DESC The input obj/file descriptor is out of range");
		srbException.setProperty("-2104", "INP_ERR_FILENAME illegal filename");
		srbException.setProperty("-2105", "INP_ERR_STAT stat error");
		srbException.setProperty("-2106",
				"INP_ERR_CHKFLAG illegal chkFlag error");
		srbException.setProperty("-2107",
				"INP_ERR_NO_SUPPORT The service is not supported");
		srbException.setProperty("-2108",
				"INP_ERR_UNREG_FILE_INVAULT Attempt to unreg file in vault");
		srbException.setProperty("-2200",
				"SYS_ERR_SRV_STARTUP SRB server startup error");
		srbException.setProperty("-2201",
				"SYS_ERR_DESC_OVERFLOW SRB server descriptor table overflow");
		srbException.setProperty("-2202",
				"SYS_ERR_REMOTE_CONN  unable to connect to remote host");
		srbException.setProperty("-2203",
				"SYS_ERR_NO_SUPPORT The service is not supported");
		srbException.setProperty("-2204", "SYS_ERR_MALLOC malloc error");
		srbException.setProperty("-2205", "SYS_ERR_HOST_ADDR host table error");
		srbException.setProperty("-2206",
				"SYS_ERR_MDAS_CAT_RET Inconsistent return from mdas");
		srbException
				.setProperty("-2207",
						"SYS_ERR_MCAT_SERVER_DOWN MCAT server down or data/hostConfig file error");
		srbException
				.setProperty(
						"-2208",
						"SYS_MCAT_CONN_RESTART MCAT connection is restarted. Not -ive because not an error");
		srbException.setProperty("-2209",
				"SYS_ERR_FILE_NOT_IN_VAULT The file is not in the SRB vault");
		srbException
				.setProperty("-2210",
						"SYS_ERR_RECV_PACKET The server has trouble receiving packet from client");
		srbException
				.setProperty("-2211",
						"SYS_ERR_SEND_PACKET The server has trouble sending packet to client");
		srbException
				.setProperty(
						"-2212",
						"SYS_ERR_NO_MCAT_HOST SYS_ERR_NO_MCAT_HOST No MCAT enabled host has been configured");
		srbException
				.setProperty("-2213",
						"PACKM_ERR_PARSE_FORMAT PACKM_ERR_PARSE_FORMAT Error in FormatDef string");
		srbException
				.setProperty("-2214",
						"SYS_ERR_NOT_REMOTE_OPR SYS_ERR_NOT_REMOTE_OPR Must be a local operation");
		srbException
				.setProperty("-2215",
						"SYS_ERR_PORTAL_CONN SYS_ERR_PORTAL_CONN Portal connection error");
		srbException.setProperty("-2216",
				"SYS_ERR_NULL_INPUT SYS_ERR_NULL_INPUT NULL input pointer");
		srbException
				.setProperty(
						"-2217",
						"SYS_ERR_PORTAL_COOKIE_SEND SYS_ERR_PORTAL_COOKIE_SEND error sending portal cookie");
		srbException
				.setProperty("-2218",
						"SYS_ERR_PTHREAD_CREATE SYS_ERR_PTHREAD_CREATE pthread_create error");
		srbException
				.setProperty("-2219",
						"SYS_ERR_TRANSFER_FAILED SYS_ERR_TRANSFER_FAILED The file transfer failed");
		srbException
				.setProperty(
						"-2220",
						"SYS_IN_MAINTENENCE_MODE SYS_IN_MAINTENENCE_MODE SRB system is in maintenence mode");
		srbException.setProperty("-2300", "ILLUS_ERR_LOGIN Login error");
		srbException.setProperty("-2301", "ILLUS_ERR_MI_EXEC mi_exec error");
		srbException.setProperty("-2302",
				"ILLUS_ERR_RESULT mi_get_result error");
		srbException.setProperty("-2303",
				"ILLUS_ERR_NEXT_ROW mi_next_row error");
		srbException.setProperty("-2304", "ILLUS_ERR_VALUE mi_value error");
		srbException.setProperty("-2305", "ILLUS_ERR_MI_DML MI_DML error");
		srbException.setProperty("-2306",
				"ILLUS_ERR__RESULT_TYPE illegal result type");
		srbException.setProperty("-2307",
				"ILLUS_ERR_NUM_ROW more than one row result");
		srbException.setProperty("-2308",
				"ILLUS_ERR_FINISH mi_query_finish error");
		srbException.setProperty("-2309",
				"ILLUS_ERR_OBJECT_TYPE result is not a large object");
		srbException.setProperty("-2310",
				"ILLUS_ERR_SEEK object seek (mi_large_object_seek) error");
		srbException.setProperty("-2311",
				"ILLUS_ERR_COPY mi_large_object_copy error");
		srbException.setProperty("-2312",
				"ILLUS_ERR_OBJ_DESC object descriptor error");
		srbException.setProperty("-2313",
				"ILLUS_ERR_CREATE mi_large_object_create error");
		srbException.setProperty("-2314",
				"ILLUS_ERR_OPEN mi_large_object_open error");
		srbException.setProperty("-2400",
				"OBJ_ERR_RES_NOT_REG resource has not been registered");
		srbException.setProperty("-2401",
				"OBJ_ERR_RES_TYPE No matching resource type");
		srbException.setProperty("-2402",
				"OBJ_ERR_OPEN_FLAG No matching open flag");
		srbException.setProperty("-2403",
				"OBJ_ERR_MDAS_HOST No MDAS enabled host");
		srbException.setProperty("-2404",
				"OBJ_ERR_COPY_LEN Bytes written does no match bytes read");
		srbException.setProperty("-2405",
				"OBJ_ERR_OBJ_TYPE No matching in srbObjTypeEntry");
		srbException
				.setProperty("-2406",
						"OBJ_ERR_CREATE_REPL No. of replica created is less than the request value");
		srbException.setProperty("-2407",
				"OBJ_ERR_PROXY_OPR_NUM Illegal Proxy Operation number");
		srbException.setProperty("-2408",
				"OBJ_ERR_INVAILD_DESC Illegal object descriptor");
		srbException.setProperty("-2409",
				"OBJ_ERR_RES_NOT_SINGLE resource is not a single resource");
		srbException.setProperty("-2410",
				"OBJ_ERR_NO_CREATE_IN_OPEN create not allowed with open");
		srbException.setProperty("-2411",
				"OBJ_ERR_REPL_COND No matching value for Replicate Condition");
		srbException
				.setProperty("-2412",
						"OBJ_ERR_NO_PERMANENT_RES No Permanent resource in logical resource");
		srbException
				.setProperty("-2413",
						"OBJ_ERR_BAD_CONTAINER_NAME The input container name does not fit format");
		srbException
				.setProperty(
						"-2414",
						"OBJ_ERR_OBJ_NOT_CONTAINER The input container name has not been registered as a container");
		srbException
				.setProperty(
						"-2415",
						"OBJ_ERR_OBJ_NO_CACHE_CONTAINER The cache container does not exist even one has been made");
		srbException
				.setProperty(
						"-2416",
						"OBJ_ERR_OUT_OF_INCONTAINER_BOUND The operation is out of the offset/size bound for the inContainer object");
		srbException
				.setProperty("-2417",
						"OBJ_ERR_MAX_SIZE_EXCEEDED The max container size has been exceeded");
		srbException
				.setProperty("-2418",
						"OBJ_ERR_CONTAINER_SIZE Internal error. Inconsistent container size");
		srbException
				.setProperty("-2419",
						"OBJ_ERR_RESC_NO_CONTAINER_SUPPORT The resource does not support container");
		srbException.setProperty("-2420",
				"OBJ_ERR_CONTAINER_HAS_NO_RESC The container has not resource");
		srbException
				.setProperty(
						"-2421",
						"OBJ_ERR_REPL_INCONT_OBJ_NOT_ALLOWED The replication operation for the inContainer object not allowed");
		srbException
				.setProperty(
						"-2422",
						"OBJ_ERR_MOVE_INCONT_OBJ_NOT_ALLOWED The move operation for the inContainer object not allowed");
		srbException
				.setProperty("-2423",
						"OBJ_ERR_OPEN_CONTAINER_NOT_ALLOWED Cannot open a Container object idirectly");
		srbException.setProperty("-2424", "OBJ_ERR_SOCKET server socket error");
		srbException.setProperty("-2425",
				"OBJ_ERR_COMMAND_PATH illegal proxy command path name");
		srbException.setProperty("-2426",
				"OBJ_ERR_SFO_CLASS SFO Class not supported");
		srbException.setProperty("-2427",
				"OBJ_ERR_SFO_BUF_OVERFLOW SFO client buffer overflow");
		srbException.setProperty("-2428",
				"OBJ_ERR_UNLINK_CONTAINER Unlinking container not allowed");
		srbException
				.setProperty("-2429",
						"SVR_OBJ_READ_BUFFER_OVERFLOW Server Object Buffer Overflow when Reading");
		srbException.setProperty("-2430",
				"OBJ_ERR_BAD_PATHNAME The input path is not a full path name");
		srbException.setProperty("-2431",
				"OBJ_ERR_BAD_STAT_INP The stat input is bad");
		srbException.setProperty("-2432",
				"OBJ_ERR_COLL_OPEN Attempt to open a collection");
		srbException
				.setProperty(
						"-2433",
						"OBJ_ERR_NOT_COLL Attempt to perform a collection operation on a non-collection");
		srbException.setProperty("-2434",
				"OBJ_ERR_LOCKFILE_OPEN Unable to open lockfile");
		srbException.setProperty("-2435",
				"OBJ_ERR_LOCKFILE_LOCK Unable to open lock lockfile");
		srbException.setProperty("-2436", "OBJ_ERR_LOCK_CMD Illegal Lock Cmd");
		srbException.setProperty("-2437",
				"OBJ_ERR_COPY_CNT The number of copies is less that requested");
		srbException
				.setProperty(
						"-2438",
						"OBJ_ERR_OBJS_FROM_DIFF_MCAT SRB objects and/or containers are not from the same MCAT");
		srbException
				.setProperty(
						"-2439",
						"OBJ_ERR_CREATE_TIMEOUT Parallel Put - A thread timed out waiting thread 0 create call");
		srbException.setProperty("-2440",
				"OBJ_ERR_BAD_CHKSUM OBJ_ERR_BAD_CHKSUM");
		srbException.setProperty("-2441",
				"OBJ_ERR_RM_LAST_AUTH_SCHEME OBJ_ERR_RM_LAST_AUTH_SCHEME");
		srbException
				.setProperty("-2500",
						"FTP_ERR_OPEN_FLAGS flags for open must be O_RDONLY or O_WRONLY");
		srbException.setProperty("-2501",
				"FTP_ERR_RETURN_VAL Wrong returned value");
		srbException.setProperty("-2502",
				"FTP_ERR_FNAME_2_LONG fileName too long");
		srbException.setProperty("-2503",
				"FTP_ERR_RESP_TIMEOUT Response timeout");
		srbException.setProperty("-2504",
				"FTP_ERR_NOT_CONNECTED Not connected to an FTP Server");
		srbException.setProperty("-2505",
				"FTP_ERR_CMD_LENGTH command length error");
		srbException.setProperty("-2700",
				"ORA_ERR_LOGIN ORA Login and password  error");
		srbException
				.setProperty("-2701", "ORA_ERR_OEXEC ORA oracle_exec error");
		srbException.setProperty("-2702",
				"ORA_MMAP_DELETE_ERROR ORA memory map not deleted error");
		srbException.setProperty("-2703",
				"ORA_MMAP_CREATE_ERROR ORA memory map not created error");
		srbException.setProperty("-2705",
				"ORA_ERR_OBJECT_TYPE ORA result is not an oracle large object");
		srbException.setProperty("-2706", "ORA_ERR_SEEK ORA object seek error");
		srbException.setProperty("-2707",
				"ORA_ERR_CREATE ORA large object create error");
		srbException.setProperty("-2708",
				"ORA_ERR_OBJ_DESC ORA object descriptor error");
		srbException.setProperty("-2709",
				"ORA_ERR_OPEN ORA large objectopen error");
		srbException.setProperty("-2710",
				"ORA_ERR_MALLOC ORA No memory available for exfd");
		srbException.setProperty("-2711",
				"ORA_LOBJ_PATH_NAME_ERROR ORA bad data pathname for sql stmt");
		srbException.setProperty("-2712",
				"ORA_ERR_CLOSE_CURSOR ORA large object could not be closed");
		srbException
				.setProperty("-2713",
						"ORA_ERR_OPEN_CURSOR ORA cursor for the query could not be created");
		srbException.setProperty("-2714",
				"ORA_ERR_SQLSTMT_PARSE ORA error parsing the sql stmt");
		srbException
				.setProperty("-2715",
						"ORA_ERR_DATA_FETCH ORA error fetching the data from the sql select results");
		srbException
				.setProperty("-2716",
						"ORA_ERR_PIECEWISE_INSERT ORA error performing piecewise insert");
		srbException.setProperty("-2718",
				"ORA_ERR_DATATYPE_BIND ORA error binding the oracle");
		srbException
				.setProperty(
						"-2719",
						"ORA_ERR_PIECEWISE_DEFINE ORA error binding variable for piecewise fetch of long datatype");
		srbException.setProperty("-2720",
				"ORA_ERR_NONEXISTENT_OBJ ORA error - a null ptr srb lobj");
		srbException.setProperty("-2721",
				"DB_TAB_OPEN_ENV_ERROR DB_TAB_OPEN_ENV_ERROR");
		srbException.setProperty("-2722",
				"DB_TAB_CONNECT_ERROR DB_TAB_CONNECT_ERROR");
		srbException.setProperty("-2723", "INP_ERR_FORMAT INP_ERR_FORMAT");
		srbException.setProperty("-2724",
				"DB_TAB_DISCONNECT_ERROR DB_TAB_DISCONNECT_ERROR");
		srbException.setProperty("-2725",
				"DB_TAB_CLOSE_ENV_ERROR DB_TAB_CLOSE_ENV_ERROR");
		srbException.setProperty("-2726",
				"TABLE_SID_GENERAL_ERROR TABLE_SID_GENERAL_ERROR");
		srbException
				.setProperty("-2727",
						"LOGICAL_EXPRESSION_EVALUATION_ERROR LOGICAL_EXPRESSION_EVALUATION_ERROR");
		srbException
				.setProperty("-2728", "DB_TAB_READ_ERROR DB_TAB_READ_ERROR");
		srbException.setProperty("-2729",
				"DB_TAB_WRITE_ERROR DB_TAB_WRITE_ERROR");
		srbException.setProperty("-2730",
				"EXPRESSION_SYNTAX_ERROR EXPRESSION_SYNTAX_ERROR");
		srbException.setProperty("-2731", "DATA_TAG_ERROR DATA_TAG_ERROR");
		srbException.setProperty("-2732",
				"RE_EVALUATION_ERROR RE_EVALUATION_ERROR");
		srbException.setProperty("-2733",
				"RE_MATCH_NOT_FOUND RE_MATCH_NOT_FOUND");
		srbException.setProperty("-2734",
				"SRB_OBJ_OPEN_ERROR SRB_OBJ_OPEN_ERROR");
		srbException.setProperty("-2735",
				"INPUT_PROPERTY_ERROR INPUT_PROPERTY_ERROR");
		srbException.setProperty("-2736",
				"TEMPLATE_FUNCTION_NOT_FOUND TEMPLATE_FUNCTION_NOT_FOUND");
		srbException.setProperty("-2737",
				"FUNCTION_CALL_PARAM_ERROR FUNCTION_CALL_PARAM_ERROR");
		srbException.setProperty("-2738",
				"DB_ERR_MALLOC DB No memory available");
		srbException.setProperty("-2739",
				"MISSING_DATA_ERROR MISSING_DATA_ERROR");
		srbException.setProperty("-2740",
				"CACHE_REQUEST_FAILED CACHE_REQUEST_FAILED");
		srbException
				.setProperty("-2741",
						"SYB_CTX_MEMORY_ALLOCATION_ERROR SYB_CTX_MEMORY_ALLOCATION_ERROR");
		srbException
				.setProperty("-2742", "SYB_CT_INIT_ERROR SYB_CT_INIT_ERROR");
		srbException.setProperty("-2743",
				"SYB_ERROR_CALLBACK_INIT_ERROR SYB_ERROR_CALLBACK_INIT_ERROR");
		srbException
				.setProperty("-2744",
						"SYB_CONNECT_MEMORY_ALLOCATION_ERROR SYB_CONNECT_MEMORY_ALLOCATION_ERROR");
		srbException.setProperty("-2745",
				"SYB_INITIATE_COMMAND_ERROR SYB_INITIATE_COMMAND_ERROR");
		srbException.setProperty("-2746",
				"SYB_SEND_COMMAND_ERROR SYB_SEND_COMMAND_ERROR");
		srbException.setProperty("-2747",
				"SYB_COMMAND_RESULTS_ERROR SYB_COMMAND_RESULTS_ERROR");
		srbException.setProperty("-2748",
				"SYB_COMMAND_RESULT_TYPE_ERROR SYB_COMMAND_RESULT_TYPE_ERROR");
		srbException.setProperty("-2749",
				"SYB_RES_INFO_ERROR SYB_RES_INFO_ERROR");
		srbException.setProperty("-2750",
				"SYB_NUM_RESULT_ERROR SYB_NUM_RESULT_ERROR");
		srbException.setProperty("-2751",
				"SYB_SELECT_DESCRIBE_ERROR SYB_SELECT_DESCRIBE_ERROR");
		srbException
				.setProperty("-2752",
						"SYB_DATA_MEMORY_ALLOCATION_ERROR SYB_DATA_MEMORY_ALLOCATION_ERROR");
		srbException
				.setProperty("-2753",
						"SYB_COMMAND_MEMORY_ALLOCATION_ERROR SYB_COMMAND_MEMORY_ALLOCATION_ERROR");
		srbException.setProperty("-2754",
				"MODIFY_QUERY_LIST_ERROR MODIFY_QUERY_LIST_ERROR");
		srbException.setProperty("-2755",
				"GET_QUERY_LIST_ERROR GET_QUERY_LIST_ERROR");
		srbException.setProperty("-2756",
				"FUNCTION_NOT_SUPPORTED FUNCTION_NOT_SUPPORTED");
		srbException.setProperty("-2757",
				"BAD_QUERY_INFO_FORNAT BAD_QUERY_INFO_FORNAT");
		srbException.setProperty("-2758",
				"USER_IN_DOMAIN_ALREADY_IN_CAT BAD_QUERY_INFO_FORNAT");
		srbException.setProperty("-2759",
				"USER_IN_GROUP_ALREADY_IN_CAT USER_IN_GROUP_ALREADY_IN_CAT");
		srbException
				.setProperty("-2760",
						"USER_AUTH_SCHEME_ALREADY_IN_CAT USER_AUTH_SCHEME_ALREADY_IN_CAT");
		srbException.setProperty("-2761",
				"MIME_EXT_ALREADY_IN_CAT MIME_EXT_ALREADY_IN_CAT");
		srbException
				.setProperty("-2762",
						"MDAS_AT_DATA_TYP_EXT_INSERTION_ERROR MDAS_AT_DATA_TYP_EXT_INSERTION_ERROR");
		srbException.setProperty("-2763",
				"ACCESS_ID_ALREADY_IN_CAT ACCESS_ID_ALREADY_IN_CAT");
		srbException
				.setProperty("-2764",
						"ACCESS_CONSTRAINT_ALREADY_IN_CAT ACCESS_CONSTRAINT_ALREADY_IN_CAT");
		srbException.setProperty("-2765",
				"INFO_ALREADY_IN_CAT INFO_ALREADY_IN_CAT");
		srbException.setProperty("-2766",
				"MDAS_AR_INFO_INSERTION_ERROR MDAS_AR_INFO_INSERTION_ERROR");
		srbException.setProperty("-2767",
				"USER_TYP_NAME_NOT_FOUND USER_TYP_NAME_NOT_FOUND");
		srbException.setProperty("-2768",
				"METADATA_ALREADY_IN_CAT METADATA_ALREADY_IN_CAT");
		srbException.setProperty("-2769",
				"USER_AUTH_SCHEME_NOT_FOUND USER_AUTH_SCHEME_NOT_FOUND");
		srbException.setProperty("-2770",
				"SERVER_LOCATION_NOT_FOUND SERVER_LOCATION_NOT_FOUND");
		srbException.setProperty("-2771",
				"SERVER_NETPREFIX_NOT_FOUND SERVER_NETPREFIX_NOT_FOUND");
		srbException.setProperty("-2772",
				"USER_NAME_NOT_FOUND USER_NAME_NOT_FOUND");
		srbException.setProperty("-2773",
				"DOMAIN_DESC_NOT_FOUND DOMAIN_DESC_NOT_FOUND");
		srbException.setProperty("-2774",
				"ZONE_NAME_NOT_FOUND ZONE_NAME_NOT_FOUND");
		srbException
				.setProperty("-2775",
						"PARENT_SERVER_LOCATION_NOT_FOUND PARENT_SERVER_LOCATION_NOT_FOUND");
		srbException.setProperty("-2776",
				"RSRC_TYP_NAME_NOT_FOUND RSRC_TYP_NAME_NOT_FOUND");
		srbException.setProperty("-2777",
				"RSRC_ACCESS_LIST_NOT_FOUND RSRC_ACCESS_LIST_NOT_FOUND");
		srbException
				.setProperty("-2778",
						"RSRC_ACCESS_PRIVILEGE_NOT_FOUND RSRC_ACCESS_PRIVILEGE_NOT_FOUND");
		srbException
				.setProperty("-2779",
						"RSRC_ACCESS_CONSTRAINT_NOT_FOUND RSRC_ACCESS_CONSTRAINT_NOT_FOUND");
		srbException.setProperty("-2780",
				"RSRC_ACCESS_ID_NOT_FOUND RSRC_ACCESS_ID_NOT_FOUND");
		srbException.setProperty("-2781",
				"DATA_TYP_NAME_NOT_FOUND DATA_TYP_NAME_NOT_FOUND");
		srbException
				.setProperty("-2782",
						"DATA_TYPE_MIME_STRING_NOT_FOUND DATA_TYPE_MIME_STRING_NOT_FOUND");
		srbException.setProperty("-2783",
				"DATA_TYPE_EXTENDERS_NOT_FOUND DATA_TYPE_EXTENDERS_NOT_FOUND");
		srbException.setProperty("-2784",
				"PARENT_DATA_TYPE_NOT_FOUND PARENT_DATA_TYPE_NOT_FOUND");
		srbException.setProperty("-2785",
				"RSRC_CLASS_NOT_FOUND RSRC_CLASS_NOT_FOUND");
		srbException.setProperty("-2786",
				"DATA_ACCESS_LIST_NOT_FOUND DATA_ACCESS_LIST_NOT_FOUND");
		srbException
				.setProperty("-2787",
						"DATA_ACCESS_PRIVILEGE_NOT_FOUND DATA_ACCESS_PRIVILEGE_NOT_FOUND");
		srbException.setProperty("-2788",
				"ACCESS_CONSTRAINT_NOT_FOUND ACCESS_CONSTRAINT_NOT_FOUND");
		srbException.setProperty("-2789",
				"DATA_ACCESS_ID_NOT_FOUND DATA_ACCESS_ID_NOT_FOUND");
		srbException.setProperty("-2790",
				"ZONE_LOCALITY_NOT_FOUND ZONE_LOCALITY_NOT_FOUND");
		srbException.setProperty("-2791",
				"ZONE_LOCN_DESC_NOT_FOUND ZONE_LOCN_DESC_NOT_FOUND");
		srbException.setProperty("-2792",
				"ZONE_NETPREFIX_NOT_FOUND ZONE_NETPREFIX_NOT_FOUND");
		srbException.setProperty("-2793",
				"ZONE_PORT_NUM_NOT_FOUND ZONE_PORT_NUM_NOT_FOUND");
		srbException
				.setProperty("-2794",
						"ZONE_ADMIN_AUTH_SCHEME_NAME_NOT_FOUND ZONE_ADMIN_AUTH_SCHEME_NAME_NOT_FOUND");
		srbException
				.setProperty("-2795",
						"ZONE_ADMIN_DISTIN_NAME_NOT_FOUND ZONE_ADMIN_DISTIN_NAME_NOT_FOUND");
		srbException.setProperty("-2796",
				"ZONE_STATUS_NOT_FOUND ZONE_STATUS_NOT_FOUND");
		srbException.setProperty("-2797",
				"ZONE_CREATE_DATE_NOT_FOUND ZONE_CREATE_DATE_NOT_FOUND");
		srbException.setProperty("-2798",
				"ZONE_MODIFY_DATE_NOT_FOUND ZONE_MODIFY_DATE_NOT_FOUND");
		srbException.setProperty("-2799",
				"ZONE_COMMENTS_NOT_FOUND ZONE_COMMENTS_NOT_FOUND");
		srbException.setProperty("-2800",
				"ZONE_CONTACT_NOT_FOUND ZONE_CONTACT_NOT_FOUND");
		srbException.setProperty("-2801",
				"ZONE_ADMIN_NAME_NOT_FOUND ZONE_ADMIN_NAME_NOT_FOUND");
		srbException
				.setProperty("-2802",
						"ZONE_ADMIN_DOMAIN_NAME_NOT_FOUND ZONE_ADMIN_DOMAIN_NAME_NOT_FOUND");
		srbException.setProperty("-2803",
				"USER_ADDRESS_NOT_FOUND USER_ADDRESS_NOT_FOUND");
		srbException.setProperty("-2804",
				"USER_PHONE_NOT_FOUND USER_PHONE_NOT_FOUND");
		srbException.setProperty("-2805",
				"USER_EMAIL_NOT_FOUND USER_EMAIL_NOT_FOUND");
		srbException.setProperty("-2806",
				"USER_CREATE_DATE_NOT_FOUND USER_CREATE_DATE_NOT_FOUND");
		srbException.setProperty("-2807",
				"USER_MODIFY_DATE_NOT_FOUND USER_MODIFY_DATE_NOT_FOUND");
		srbException.setProperty("-2808",
				"USER_DISTIN_NAME_NOT_FOUND USER_DISTIN_NAME_NOT_FOUND");
		srbException.setProperty("-2809",
				"USER_GROUP_NAME_NOT_FOUND USER_GROUP_NAME_NOT_FOUND");
		srbException
				.setProperty("-2810",
						"USER_GROUP_ZONE_NAME_NOT_FOUND USER_GROUP_ZONE_NAME_NOT_FOUND");
		srbException
				.setProperty("-2811",
						"USER_GROUP_DOMAIN_DESC_NOT_FOUND USER_GROUP_DOMAIN_DESC_NOT_FOUND");
		srbException.setProperty("-2813",
				"UDSMD_USER0_NOT_FOUND UDSMD_USER0_NOT_FOUND");
		srbException.setProperty("-2814",
				"UDSMD_USER1_NOT_FOUND UDSMD_USER1_NOT_FOUND");
		srbException.setProperty("-2815",
				"UDSMD_USER2_NOT_FOUND UDSMD_USER2_NOT_FOUND");
		srbException.setProperty("-2816",
				"UDSMD_USER3_NOT_FOUND UDSMD_USER3_NOT_FOUND");
		srbException.setProperty("-2817",
				"UDSMD_USER4_NOT_FOUND UDSMD_USER4_NOT_FOUND");
		srbException.setProperty("-2818",
				"UDSMD_USER5_NOT_FOUND UDSMD_USER5_NOT_FOUND");
		srbException.setProperty("-2819",
				"UDSMD_USER6_NOT_FOUND UDSMD_USER6_NOT_FOUND");
		srbException.setProperty("-2820",
				"UDSMD_USER7_NOT_FOUND UDSMD_USER7_NOT_FOUND");
		srbException.setProperty("-2821",
				"UDSMD_USER8_NOT_FOUND UDSMD_USER8_NOT_FOUND");
		srbException.setProperty("-2822",
				"UDSMD_USER9_NOT_FOUND UDSMD_USER9_NOT_FOUND");
		srbException.setProperty("-2823",
				"UDIMD_USER0_NOT_FOUND UDIMD_USER0_NOT_FOUND");
		srbException.setProperty("-2824",
				"UDIMD_USER1_NOT_FOUND UDIMD_USER1_NOT_FOUND");
		srbException
				.setProperty("-2825",
						"USER_UDEF_MDATA_MODIFY_DATE_NOT_FOUND USER_UDEF_MDATA_MODIFY_DATE_NOT_FOUND");
		srbException.setProperty("-2827",
				"RSRC_NAME_NOT_FOUND RSRC_NAME_NOT_FOUND");
		srbException.setProperty("-2828",
				"LOCATION_NAME_NOT_FOUND LOCATION_NAME_NOT_FOUND");
		srbException.setProperty("-2829",
				"RSRC_DEFAULT_PATH_NOT_FOUND RSRC_DEFAULT_PATH_NOT_FOUND");
		srbException.setProperty("-2830",
				"RSRC_REPL_ENUM_NOT_FOUND RSRC_REPL_ENUM_NOT_FOUND");
		srbException.setProperty("-2831",
				"RSRC_COMMENTS_NOT_FOUND RSRC_COMMENTS_NOT_FOUND");
		srbException.setProperty("-2832",
				"RSRC_CREATE_DATE_NOT_FOUND RSRC_CREATE_DATE_NOT_FOUND");
		srbException.setProperty("-2833",
				"RSRC_MODIFY_DATE_NOT_FOUND RSRC_MODIFY_DATE_NOT_FOUND");
		srbException.setProperty("-2834",
				"PHY_RSRC_NAME_NOT_FOUND PHY_RSRC_NAME_NOT_FOUND");
		srbException.setProperty("-2835",
				"RSRC_MAX_OBJ_SIZE_NOT_FOUND RSRC_MAX_OBJ_SIZE_NOT_FOUND");
		srbException.setProperty("-2836",
				"RSRC_OWNER_NAME_NOT_FOUND RSRC_OWNER_NAME_NOT_FOUND");
		srbException.setProperty("-2837",
				"RSRC_OWNER_DOMAIN_NOT_FOUND RSRC_OWNER_DOMAIN_NOT_FOUND");
		srbException.setProperty("-2838",
				"RSRC_OWNER_ZONE_NOT_FOUND RSRC_OWNER_ZONE_NOT_FOUND");
		srbException
				.setProperty("-2840",
						"RSRC_MLSEC_LATENCY_MAX_NOT_FOUND RSRC_MLSEC_LATENCY_MAX_NOT_FOUND");
		srbException
				.setProperty("-2841",
						"RSRC_MLSEC_LATENCY_MIN_NOT_FOUND RSRC_MLSEC_LATENCY_MIN_NOT_FOUND");
		srbException.setProperty("-2842",
				"RSRC_MBPS_BANDWIDTH_NOT_FOUND RSRC_MBPS_BANDWIDTH_NOT_FOUND");
		srbException
				.setProperty("-2843",
						"RSRC_CONCURRENCY_MAX_NOT_FOUND RSRC_CONCURRENCY_MAX_NOT_FOUND");
		srbException
				.setProperty("-2844",
						"RSRC_NUM_OF_HIERARCHIES_NOT_FOUND RSRC_NUM_OF_HIERARCHIES_NOT_FOUND");
		srbException.setProperty("-2845",
				"RSRC_NUM_OF_STRIPES_NOT_FOUND RSRC_NUM_OF_STRIPES_NOT_FOUND");
		srbException.setProperty("-2846",
				"RSRC_MEGAB_CAPACITY_NOT_FOUND RSRC_MEGAB_CAPACITY_NOT_FOUND");
		srbException.setProperty("-2847",
				"UDSMD_RSRC0_NOT_FOUND UDSMD_RSRC0_NOT_FOUND");
		srbException.setProperty("-2848",
				"UDSMD_RSRC1_NOT_FOUND UDSMD_RSRC1_NOT_FOUND");
		srbException.setProperty("-2849",
				"UDSMD_RSRC2_NOT_FOUND UDSMD_RSRC2_NOT_FOUND");
		srbException.setProperty("-2850",
				"UDSMD_RSRC3_NOT_FOUND UDSMD_RSRC3_NOT_FOUND");
		srbException.setProperty("-2851",
				"UDSMD_RSRC4_NOT_FOUND UDSMD_RSRC4_NOT_FOUND");
		srbException.setProperty("-2852",
				"UDSMD_RSRC5_NOT_FOUND UDSMD_RSRC5_NOT_FOUND");
		srbException.setProperty("-2853",
				"UDSMD_RSRC6_NOT_FOUND UDSMD_RSRC6_NOT_FOUND");
		srbException.setProperty("-2854",
				"UDSMD_RSRC7_NOT_FOUND UDSMD_RSRC7_NOT_FOUND");
		srbException.setProperty("-2855",
				"UDSMD_RSRC8_NOT_FOUND UDSMD_RSRC8_NOT_FOUND");
		srbException.setProperty("-2856",
				"UDSMD_RSRC9_NOT_FOUND UDSMD_RSRC9_NOT_FOUND");
		srbException.setProperty("-2857",
				"UDIMD_RSRC0_NOT_FOUND UDIMD_RSRC0_NOT_FOUND");
		srbException.setProperty("-2858",
				"UDIMD_RSRC1_NOT_FOUND UDIMD_RSRC1_NOT_FOUND");
		srbException
				.setProperty("-2859",
						"RSRC_UDEF_MDATA_MODIFY_DATE_NOT_FOUND RSRC_UDEF_MDATA_MODIFY_DATE_NOT_FOUND");
		srbException.setProperty("-2860",
				"PARENT_USER_TYPE_NOT_FOUND PARENT_USER_TYPE_NOT_FOUND");
		srbException.setProperty("-2861",
				"PARENT_DOMAIN_DESC_NOT_FOUND PARENT_DOMAIN_DESC_NOT_FOUND");
		srbException.setProperty("-2862",
				"RSRC_ACCS_USER_NAME_NOT_FOUND RSRC_ACCS_USER_NAME_NOT_FOUND");
		srbException
				.setProperty("-2863",
						"RSRC_ACCS_USER_DOMAIN_NOT_FOUND RSRC_ACCS_USER_DOMAIN_NOT_FOUND");
		srbException.setProperty("-2864",
				"RSRC_ACCS_USER_ZONE_NOT_FOUND RSRC_ACCS_USER_ZONE_NOT_FOUND");
		srbException.setProperty("-2865",
				"LOCK_OWNERSHIP_ERROR LOCK_OWNERSHIP_ERROR");
		srbException.setProperty("-2866",
				"USER_IS_NOT_OWNER_ERROR USER_IS_NOT_OWNER_ERROR");
		srbException.setProperty("-2867",
				"LOCAL_ZONE_NOT_FOUND LOCAL_ZONE_NOT_FOUND");
		srbException.setProperty("-2868",
				"PARENT_RSRC_TYPE_NOT_FOUND PARENT_RSRC_TYPE_NOT_FOUND");
		srbException
				.setProperty("-2869",
						"RSRC_ACCS_GRPUSER_NAME_NOT_FOUND RSRC_ACCS_GRPUSER_NAME_NOT_FOUND");
		srbException
				.setProperty("-2870",
						"RSRC_ACCS_GRPUSER_DOMAIN_NOT_FOUND RSRC_ACCS_GRPUSER_DOMAIN_NOT_FOUND");
		srbException
				.setProperty("-2871",
						"RSRC_ACCS_GRPUSER_ZONE_NOT_FOUND RSRC_ACCS_GRPUSER_ZONE_NOT_FOUND");
		srbException
				.setProperty("-2872",
						"COLLECTION_CONT_NAME_NOT_FOUND COLLECTION_CONT_NAME_NOT_FOUND");
		srbException.setProperty("-2873",
				"COLLECTION_NAME_NOT_FOUND COLLECTION_NAME_NOT_FOUND");
		srbException
				.setProperty("-2874",
						"COLL_ACCS_ACCESS_CONSTRAINT_NOT_FOUND COLL_ACCS_ACCESS_CONSTRAINT_NOT_FOUND");
		srbException
				.setProperty("-2875",
						"COLL_ACCS_GRPUSER_DOMAIN_NOT_FOUND COLL_ACCS_GRPUSER_DOMAIN_NOT_FOUND");
		srbException
				.setProperty("-2876",
						"COLL_ACCS_GRPUSER_NAME_NOT_FOUND COLL_ACCS_GRPUSER_NAME_NOT_FOUND");
		srbException
				.setProperty("-2877",
						"COLL_ACCS_GRPUSER_ZONE_NOT_FOUND COLL_ACCS_GRPUSER_ZONE_NOT_FOUND");
		srbException.setProperty("-2878",
				"COLL_ANNOTATION_NOT_FOUND COLL_ANNOTATION_NOT_FOUND");
		srbException
				.setProperty("-2879",
						"COLL_ANNOTATION_TIMESTAMP_NOT_FOUND COLL_ANNOTATION_TIMESTAMP_NOT_FOUND");
		srbException
				.setProperty("-2880",
						"COLL_ANNOTATION_TYPE_NOT_FOUND COLL_ANNOTATION_TYPE_NOT_FOUND");
		srbException
				.setProperty("-2881",
						"COLL_ANNOTATION_USERDOMAIN_NOT_FOUND COLL_ANNOTATION_USERDOMAIN_NOT_FOUND");
		srbException
				.setProperty("-2882",
						"COLL_ANNOTATION_USERNAME_NOT_FOUND COLL_ANNOTATION_USERNAME_NOT_FOUND");
		srbException
				.setProperty("-2883",
						"COLL_ANNOTATION_USER_ZONE_NOT_FOUND COLL_ANNOTATION_USER_ZONE_NOT_FOUND");
		srbException.setProperty("-2884",
				"COLL_COMMENTS_NOT_FOUND COLL_COMMENTS_NOT_FOUND");
		srbException
				.setProperty("-2885",
						"COLL_CREATE_TIMESTAMP_NOT_FOUND COLL_CREATE_TIMESTAMP_NOT_FOUND");
		srbException
				.setProperty("-2886",
						"COLL_MODIFY_TIMESTAMP_NOT_FOUND COLL_MODIFY_TIMESTAMP_NOT_FOUND");
		srbException.setProperty("-2887",
				"COLL_OWNER_DOMAIN_NOT_FOUND COLL_OWNER_DOMAIN_NOT_FOUND");
		srbException.setProperty("-2888",
				"COLL_OWNER_NAME_NOT_FOUND COLL_OWNER_NAME_NOT_FOUND");
		srbException.setProperty("-2889",
				"COLL_OWNER_ZONE_NOT_FOUND COLL_OWNER_ZONE_NOT_FOUND");
		srbException
				.setProperty("-2890",
						"COLL_UDEF_MDATA_CREATE_DATE_NOT_FOUND COLL_UDEF_MDATA_CREATE_DATE_NOT_FOUND");
		srbException
				.setProperty("-2891",
						"COLL_UDEF_MDATA_MODIFY_DATE_NOT_FOUND COLL_UDEF_MDATA_MODIFY_DATE_NOT_FOUND");
		srbException
				.setProperty("-2892",
						"CONTAINER_FOR_COLLECTION_NOT_FOUND CONTAINER_FOR_COLLECTION_NOT_FOUND");
		srbException
				.setProperty("-2893",
						"CONTAINER_LOG_RSRC_NAME_NOT_FOUND CONTAINER_LOG_RSRC_NAME_NOT_FOUND");
		srbException.setProperty("-2894",
				"CONTAINER_MAX_SIZE_NOT_FOUND CONTAINER_MAX_SIZE_NOT_FOUND");
		srbException.setProperty("-2895",
				"CONTAINER_NAME_NOT_FOUND CONTAINER_NAME_NOT_FOUND");
		srbException
				.setProperty("-2896",
						"DATA_ACCS_GRPUSER_DOMAIN_NOT_FOUND DATA_ACCS_GRPUSER_DOMAIN_NOT_FOUND");
		srbException
				.setProperty("-2879",
						"DATA_ACCS_GRPUSER_NAME_NOT_FOUND DATA_ACCS_GRPUSER_NAME_NOT_FOUND");
		srbException
				.setProperty("-2898",
						"DATA_ACCS_GRPUSER_ZONE_NOT_FOUND DATA_ACCS_GRPUSER_ZONE_NOT_FOUND");
		srbException.setProperty("-2899",
				"DATA_ANNOTATION_NOT_FOUND DATA_ANNOTATION_NOT_FOUND");
		srbException
				.setProperty("-2900",
						"DATA_ANNOTATION_POSITION_NOT_FOUND DATA_ANNOTATION_POSITION_NOT_FOUND");
		srbException
				.setProperty("-2901",
						"DATA_ANNOTATION_TIMESTAMP_NOT_FOUND DATA_ANNOTATION_TIMESTAMP_NOT_FOUND");
		srbException
				.setProperty("-2902",
						"DATA_ANNOTATION_USERDOMAIN_NOT_FOUND DATA_ANNOTATION_USERDOMAIN_NOT_FOUND");
		srbException
				.setProperty("-2903",
						"DATA_ANNOTATION_USERNAME_NOT_FOUND DATA_ANNOTATION_USERNAME_NOT_FOUND");
		srbException
				.setProperty("-2904",
						"DATA_ANNOTATION_USER_ZONE_NOT_FOUND DATA_ANNOTATION_USER_ZONE_NOT_FOUND");
		srbException.setProperty("-2905",
				"DATA_AUDITFLAG_NOT_FOUND DATA_AUDITFLAG_NOT_FOUND");
		srbException.setProperty("-2906",
				"DATA_CHECKSUM_NOT_FOUND DATA_CHECKSUM_NOT_FOUND");
		srbException.setProperty("-2907",
				"DATA_COMMENTS_NOT_FOUND DATA_COMMENTS_NOT_FOUND");
		srbException.setProperty("-2908",
				"DATA_CONT_NAME_NOT_FOUND DATA_CONT_NAME_NOT_FOUND");
		srbException
				.setProperty("-2909",
						"DATA_CREATE_TIMESTAMP_NOT_FOUND DATA_CREATE_TIMESTAMP_NOT_FOUND");
		srbException.setProperty("-2910",
				"DATA_EXPIRE_DATE_2_NOT_FOUND DATA_EXPIRE_DATE_2_NOT_FOUND");
		srbException.setProperty("-2911",
				"DATA_EXPIRY_DATE_NOT_FOUND DATA_EXPIRY_DATE_NOT_FOUND");
		srbException.setProperty("-2912",
				"DATA_HIDE_NOT_FOUND DATA_HIDE_NOT_FOUND");
		srbException.setProperty("-2913",
				"DATA_IS_COMPRESSED_NOT_FOUND DATA_IS_COMPRESSED_NOT_FOUND");
		srbException.setProperty("-2914",
				"DATA_IS_DELETED_NOT_FOUND DATA_IS_DELETED_NOT_FOUND");
		srbException.setProperty("-2915",
				"DATA_IS_ENCRYPTED_NOT_FOUND DATA_IS_ENCRYPTED_NOT_FOUND");
		srbException
				.setProperty("-2916",
						"DATA_LAST_ACCESS_TIMESTAMP_NOT_FOUND DATA_LAST_ACCESS_TIMESTAMP_NOT_FOUND");
		srbException.setProperty("-2917",
				"DATA_LOCK_DESC_NOT_FOUND DATA_LOCK_DESC_NOT_FOUND");
		srbException.setProperty("-2918",
				"DATA_LOCK_EXPIRY_NOT_FOUND DATA_LOCK_EXPIRY_NOT_FOUND");
		srbException
				.setProperty("-2919",
						"DATA_LOCK_OWNER_DOMAIN_NOT_FOUND DATA_LOCK_OWNER_DOMAIN_NOT_FOUND");
		srbException
				.setProperty("-2920",
						"DATA_LOCK_OWNER_NAME_NOT_FOUND DATA_LOCK_OWNER_NAME_NOT_FOUND");
		srbException
				.setProperty("-2921",
						"DATA_LOCK_OWNER_ZONE_NOT_FOUND DATA_LOCK_OWNER_ZONE_NOT_FOUND");
		srbException.setProperty("-2922",
				"DATA_NAME_NOT_FOUND DATA_NAME_NOT_FOUND");
		srbException.setProperty("-2923",
				"DATA_OWNER_DOMAIN_NOT_FOUND DATA_OWNER_DOMAIN_NOT_FOUND");
		srbException.setProperty("-2924",
				"DATA_OWNER_NOT_FOUND DATA_OWNER_NOT_FOUND");
		srbException.setProperty("-2925",
				"DATA_OWNER_ZONE_NOT_FOUND DATA_OWNER_ZONE_NOT_FOUND");
		srbException.setProperty("-2926",
				"DATA_PIN_EXPIRY_NOT_FOUND DATA_PIN_EXPIRY_NOT_FOUND");
		srbException
				.setProperty("-2927",
						"DATA_PIN_OWNER_DOMAIN_NOT_FOUND DATA_PIN_OWNER_DOMAIN_NOT_FOUND");
		srbException.setProperty("-2928",
				"DATA_PIN_OWNER_NAME_NOT_FOUND DATA_PIN_OWNER_NAME_NOT_FOUND");
		srbException.setProperty("-2929",
				"DATA_PIN_OWNER_ZONE_NOT_FOUND DATA_PIN_OWNER_ZONE_NOT_FOUND");
		srbException.setProperty("-2930",
				"DATA_PIN_VAL_NOT_FOUND DATA_PIN_VAL_NOT_FOUND");
		srbException.setProperty("-2931",
				"DATA_REPL_ENUM_NOT_FOUND DATA_REPL_ENUM_NOT_FOUND");
		srbException.setProperty("-2932",
				"DATA_SEG_NUM_NOT_FOUND DATA_SEG_NUM_NOT_FOUND");
		srbException
				.setProperty(
						"-2933",
						"DATA_TYPE_FOR_CONTAINER_FOR_COLLECTION_NOT_FOUND DATA_TYPE_FOR_CONTAINER_FOR_COLLECTION_NOT_FOUND");
		srbException
				.setProperty("-2934",
						"DATA_UDEF_MDATA_CREATE_DATE_NOT_FOUND DATA_UDEF_MDATA_CREATE_DATE_NOT_FOUND");
		srbException
				.setProperty("-2935",
						"DATA_UDEF_MDATA_MODIFY_DATE_NOT_FOUND DATA_UDEF_MDATA_MODIFY_DATE_NOT_FOUND");
		srbException.setProperty("-2936",
				"DATA_VER_NUM_NOT_FOUND DATA_VER_NUM_NOT_FOUND");
		srbException.setProperty("-2937", "DCOLLECTION_NAME DCOLLECTION_NAME");
		srbException.setProperty("-2938",
				"IS_DIRTY_NOT_FOUND IS_DIRTY_NOT_FOUND");
		srbException.setProperty("-2939",
				"ITEM_ALREADY_IN_CAT ITEM_ALREADY_IN_CAT");
		srbException.setProperty("-2940",
				"METADATA_NUM_COLL_NOT_FOUND METADATA_NUM_COLL_NOT_FOUND");
		srbException.setProperty("-2941",
				"METADATA_NUM_NOT_FOUND METADATA_NUM_NOT_FOUND");
		srbException.setProperty("-2942",
				"METADATA_NUM_RSRC_NOT_FOUND METADATA_NUM_RSRC_NOT_FOUND");
		srbException.setProperty("-2943",
				"METADATA_NUM_USER_NOT_FOUND METADATA_NUM_USER_NOT_FOUND");
		srbException.setProperty("-2944", "OFFSET_NOT_FOUND OFFSET_NOT_FOUND");
		srbException
				.setProperty("-2945",
						"PARENT_COLLECTION_NAME_NOT_FOUND PARENT_COLLECTION_NAME_NOT_FOUND");
		srbException.setProperty("-2946",
				"PATH_NAME_NOT_FOUND PATH_NAME_NOT_FOUND");
		srbException.setProperty("-2947",
				"REPL_TIMESTAMP_NOT_FOUND REPL_TIMESTAMP_NOT_FOUND");
		srbException
				.setProperty("-2948",
						"RSRC_UDEF_MDATA_CREATE_DATE_NOT_FOUND RSRC_UDEF_MDATA_CREATE_DATE_NOT_FOUND");
		srbException.setProperty("-2949", "SIZE_NOT_FOUND SIZE_NOT_FOUND");
		srbException.setProperty("-2950", "UDIMD0_NOT_FOUND UDIMD0_NOT_FOUND");
		srbException.setProperty("-2951", "UDIMD1_NOT_FOUND UDIMD1_NOT_FOUND");
		srbException.setProperty("-2952",
				"UDIMD_COLL0_NOT_FOUND UDIMD_COLL0_NOT_FOUND");
		srbException.setProperty("-2953",
				"UDIMD_COLL1_NOT_FOUND UDIMD_COLL1_NOT_FOUND");
		srbException.setProperty("-2954", "UDSMD0_NOT_FOUND UDSMD0_NOT_FOUND");
		srbException.setProperty("-2955", "UDSMD1_NOT_FOUND UDSMD1_NOT_FOUND");
		srbException.setProperty("-2956", "UDSMD2_NOT_FOUND UDSMD2_NOT_FOUND");
		srbException.setProperty("-2957", "UDSMD3_NOT_FOUND UDSMD3_NOT_FOUND");
		srbException.setProperty("-2958", "UDSMD4_NOT_FOUND UDSMD4_NOT_FOUND");
		srbException.setProperty("-2959", "UDSMD5_NOT_FOUND UDSMD5_NOT_FOUND");
		srbException.setProperty("-2960", "UDSMD6_NOT_FOUND UDSMD6_NOT_FOUND");
		srbException.setProperty("-2961", "UDSMD7_NOT_FOUND UDSMD7_NOT_FOUND");
		srbException.setProperty("-2962", "UDSMD8_NOT_FOUND UDSMD8_NOT_FOUND");
		srbException.setProperty("-2963", "UDSMD9_NOT_FOUND UDSMD9_NOT_FOUND");
		srbException.setProperty("-2964",
				"UDSMD_COLL0_NOT_FOUND UDSMD_COLL0_NOT_FOUND");
		srbException.setProperty("-2965",
				"UDSMD_COLL1_NOT_FOUND UDSMD_COLL1_NOT_FOUND");
		srbException.setProperty("-2966",
				"UDSMD_COLL2_NOT_FOUND UDSMD_COLL2_NOT_FOUND");
		srbException.setProperty("-2967",
				"UDSMD_COLL3_NOT_FOUND UDSMD_COLL3_NOT_FOUND");
		srbException.setProperty("-2968",
				"UDSMD_COLL4_NOT_FOUND UDSMD_COLL4_NOT_FOUND");
		srbException.setProperty("-2969",
				"UDSMD_COLL5_NOT_FOUND UDSMD_COLL5_NOT_FOUND");
		srbException.setProperty("-2970",
				"UDSMD_COLL6_NOT_FOUND UDSMD_COLL6_NOT_FOUND");
		srbException.setProperty("-2971",
				"UDSMD_COLL7_NOT_FOUND UDSMD_COLL7_NOT_FOUND");
		srbException.setProperty("-2972",
				"UDSMD_COLL8_NOT_FOUND UDSMD_COLL8_NOT_FOUND");
		srbException.setProperty("-2973",
				"UDSMD_COLL9_NOT_FOUND UDSMD_COLL9_NOT_FOUND");
		srbException
				.setProperty("-2974",
						"USER_UDEF_MDATA_CREATE_DATE_NOT_FOUND USER_UDEF_MDATA_CREATE_DATE_NOT_FOUND");
		srbException.setProperty("-2975",
				"DATA_LOCK_NUM_NOT_FOUND DATA_LOCK_NUM_NOT_FOUND");
		srbException.setProperty("-2976",
				"CONTAINER_REPL_ENUM_NOT_FOUND CONTAINER_REPL_ENUM_NOT_FOUND");
		srbException.setProperty("-2977", "GUID_NOT_FOUND GUID_NOT_FOUND");
		srbException.setProperty("-2978",
				"GUID_FLAG_NOT_FOUND GUID_FLAG_NOT_FOUND");
		srbException.setProperty("-2979",
				"GUID_TIME_STAMP_NOT_FOUND GUID_TIME_STAMP_NOT_FOUND");
		srbException.setProperty("-3001",
				"MCAT_OPEN_ENV_ERROR MCAT_OPEN_ENV_ERROR");
		srbException.setProperty("-3002",
				"MCAT_CONNECT_ERROR MCAT_CONNECT_ERROR");
		srbException.setProperty("-3003",
				"MCAT_CLOSE_ENV_ERROR MCAT_CLOSE_ENV_ERROR");
		srbException.setProperty("-3004",
				"MCAT_DISCONNECT_ERROR MCAT_DISCONNECT_ERROR");
		srbException.setProperty("-3005",
				"MCAT_INQUIRE_ERROR MCAT_INQUIRE_ERROR");
		srbException.setProperty("-3006",
				"MEMORY_ALLOCATION_ERROR MEMORY_ALLOCATION_ERROR");
		srbException.setProperty("-3007",
				"ENV_MEMORY_ALLOCATION_ERROR ENV_MEMORY_ALLOCATION_ERROR");
		srbException.setProperty("-3008",
				"STMT_MEMORY_ALLOCATION_ERROR STMT_MEMORY_ALLOCATION_ERROR");
		srbException
				.setProperty("-3009",
						"CONNECT_MEMORY_ALLOCATION_ERROR CONNECT_MEMORY_ALLOCATION_ERROR");
		srbException.setProperty("-3010",
				"MCAT_FREE_STMT_ERROR MCAT_FREE_STMT_ERROR");
		srbException.setProperty("-3011",
				"MCAT_AUTOCOMMIT_TURNOFF_ERROR MCAT_AUTOCOMMIT_TURNOFF_ERROR");
		srbException.setProperty("-3012",
				"MCAT_OPTION_SETTING_ERROR MCAT_OPTION_SETTING_ERROR");
		srbException.setProperty("-3013",
				"MCAT_TRANSACT_CLOSE_ERROR MCAT_TRANSACT_CLOSE_ERROR");
		srbException.setProperty("-3014",
				"MCAT_CURSOR_OPEN_ERROR MCAT_CURSOR_OPEN_ERROR");
		srbException.setProperty("-3015", "MCAT_EXEC_ERROR MCAT_EXEC_ERROR");
		srbException.setProperty("-3016",
				"MCAT_NUM_RESULT_ERROR MCAT_NUM_RESULT_ERROR");
		srbException.setProperty("-3017",
				"MCAT_SELECT_DESCRIBE_ERROR MCAT_SELECT_DESCRIBE_ERROR");
		srbException.setProperty("-3018",
				"MCAT_SELECT_DEFINE_ERROR MCAT_SELECT_DEFINE_ERROR");
		srbException.setProperty("-3019", "MCAT_FETCH_ERROR MCAT_FETCH_ERROR");
		srbException.setProperty("-3020", "MCAT_PARSE_ERROR MCAT_PARSE_ERROR");
		srbException.setProperty("-3021",
				"MCAT_NO_DATA_FOUND MCAT_NO_DATA_FOUND");
		srbException.setProperty("-3022",
				"MCAT_MAX_GRAPH_PATH_OVERFLOW MCAT_MAX_GRAPH_PATH_OVERFLOW");
		srbException
				.setProperty("-3023",
						"MCAT_MAX_FKREL_ENTRIES_OVERFLOW MCAT_MAX_FKREL_ENTRIES_OVERFLOW");
		srbException
				.setProperty("-3024",
						"MCAT_MAX_ATTR_ENTRIES_OVERFLOW MCAT_MAX_ATTR_ENTRIES_OVERFLOW");
		srbException.setProperty("-3025",
				"MCAT_MAX_GRAPH_PATH_UNDERFLOW MCAT_MAX_GRAPH_PATH_UNDERFLOW");
		srbException
				.setProperty("-3026",
						"MCAT_MAX_FKREL_ENTRIES_UNDERFLOW MCAT_MAX_FKREL_ENTRIES_UNDERFLOW");
		srbException
				.setProperty("-3027",
						"MCAT_MAX_ATTR_ENTRIES_UNDERFLOW MCAT_MAX_ATTR_ENTRIES_UNDERFLOW");
		srbException
				.setProperty("-3028", "MEMORY_FREE_ERROR MEMORY_FREE_ERROR");
		srbException.setProperty("-3029",
				"MCAT_NO_MORE_CONTEXTS MCAT_NO_MORE_CONTEXTS");
		srbException.setProperty("-3030",
				"MCAT_MAX_QUERY_LIMIT_EXCEEDED MCAT_MAX_QUERY_LIMIT_EXCEEDED");
		srbException.setProperty("-3031",
				"MCAT_RESOURCE_NOT_IN_CAT MCAT_RESOURCE_NOT_IN_CAT");
		srbException.setProperty("-3032",
				"MCAT_USER_NOT_IN_DOMN MCAT_USER_NOT_IN_DOMN");
		srbException.setProperty("-3033",
				"OPERATION_NOT_IMPLEMENTED OPERATION_NOT_IMPLEMENTED");
		srbException
				.setProperty("-3034",
						"QUERIES_IN_UNKNOWN_EXTERNAL_RESOURCES QUERIES_IN_UNKNOWN_EXTERNAL_RESOURCES");
		srbException.setProperty("-3035", "TEMP_TABLE_ERROR TEMP_TABLE_ERROR");
		srbException
				.setProperty("-3036",
						"ERROR_IN_QUERY_EXPECTING_SELECT ERROR_IN_QUERY_EXPECTING_SELECT");
		srbException.setProperty("-3037",
				"SIZE_ERROR_IN_QUERY SIZE_ERROR_IN_QUERY");
		srbException
				.setProperty("-3038",
						"NO_DIMENSION_FUNCTION_AVAILABLE NO_DIMENSION_FUNCTION_AVAILABLE");
		srbException.setProperty("-3039",
				"UNKNOWN_ATTRIBUTE_DIMENSION UNKNOWN_ATTRIBUTE_DIMENSION");
		srbException.setProperty("-3050",
				"DBLOBJ_PATH_NAME_ERROR DBLOBJ_PATH_NAME_ERROR");
		srbException.setProperty("-3051",
				"DBLOBJ_RESOURCE_NAME_ERROR DBLOBJ_RESOURCE_NAME_ERROR");
		srbException
				.setProperty("-3052", "DBLOBJ_TYPE_ERROR DBLOBJ_TYPE_ERROR");
		srbException.setProperty("-3053",
				"DBLOBJ_PUTENV_ERROR DBLOBJ_PUTENV_ERROR");
		srbException.setProperty("-3054",
				"DBLOBJ_ALLOC_ENV_ERROR DBLOBJ_ALLOC_ENV_ERROR");
		srbException.setProperty("-3055",
				"DBLOBJ_ALLOC_CONNECT_ERROR DBLOBJ_ALLOC_CONNECT_ERROR");
		srbException.setProperty("-3056",
				"DBLOBJ_OPTION_SETTING_ERROR DBLOBJ_OPTION_SETTING_ERROR");
		srbException.setProperty("-3057",
				"DBLOBJ_CONNECT_ERROR DBLOBJ_CONNECT_ERROR");
		srbException.setProperty("-3058",
				"DBLOBJ_ALLOC_STMT_ERROR DBLOBJ_ALLOC_STMT_ERROR");
		srbException.setProperty("-3059",
				"DBLOBJ_FREE_STMT_ERROR DBLOBJ_FREE_STMT_ERROR");
		srbException.setProperty("-3060",
				"DBLOBJ_DISCONNECT_ERROR DBLOBJ_DISCONNECT_ERROR");
		srbException.setProperty("-3061",
				"DBLOBJ_FREE_CONNECT_ERROR DBLOBJ_FREE_CONNECT_ERROR");
		srbException.setProperty("-3062",
				"DBLOBJ_FREE_ENV_ERROR DBLOBJ_FREE_ENV_ERROR");
		srbException
				.setProperty("-3063", "DBLOBJ_SIZE_ERROR DBLOBJ_SIZE_ERROR");
		srbException.setProperty("-3064",
				"DBLOBJ_STATE_ALLOC_ERROR DBLOBJ_STATE_ALLOC_ERROR");
		srbException.setProperty("-3101",
				"METADATA_ACCESS_ERROR METADATA_ACCESS_ERROR");
		srbException.setProperty("-3102",
				"METADATA_COUNTER_ERROR METADATA_COUNTER_ERROR");
		srbException.setProperty("-3103",
				"METADATA_INSERTION_ERROR METADATA_INSERTION_ERROR");
		srbException.setProperty("-3104",
				"METADATA_DELETION_ERROR METADATA_DELETION_ERROR");
		srbException.setProperty("-3105",
				"METADATA_UPDATE_ERROR METADATA_UPDATE_ERROR");
		srbException.setProperty("-3106", "GET_TIME_ERROR GET_TIME_ERROR");
		srbException.setProperty("-3107",
				"DATA_ID_COUNTER_ERROR DATA_ID_COUNTER_ERROR");
		srbException.setProperty("-3108",
				"RSRC_ID_COUNTER_ERROR RSRC_ID_COUNTER_ERROR");
		srbException.setProperty("-3109",
				"METH_ID_COUNTER_ERROR METH_ID_COUNTER_ERROR");
		srbException.setProperty("-3110",
				"USER_ID_COUNTER_ERROR USER_ID_COUNTER_ERROR");
		srbException.setProperty("-3111",
				"MDAS_USER_REGISTRATION_ERROR MDAS_USER_REGISTRATION_ERROR");
		srbException.setProperty("-3112",
				"MDAS_DATA_REGISTRATION_ERROR MDAS_DATA_REGISTRATION_ERROR");
		srbException.setProperty("-3113",
				"MDAS_RSRC_REGISTRATION_ERROR MDAS_RSRC_REGISTRATION_ERROR");
		srbException.setProperty("-3114",
				"MDAS_METH_REGISTRATION_ERROR MDAS_METH_REGISTRATION_ERROR");
		srbException
				.setProperty("-3115",
						"MDAS_USER_GROUP_REGISTRATION_ERROR MDAS_USER_GROUP_REGISTRATION_ERROR");
		srbException.setProperty("-3116",
				"MDAS_HIERARCHY_ERROR MDAS_HIERARCHY_ERROR");
		srbException.setProperty("-3117",
				"MDAS_HIERARCHY_OVERFLOW_ERROR MDAS_HIERARCHY_OVERFLOW_ERROR");
		srbException.setProperty("-3118", "TOKEN_NOT_IN_CAT TOKEN_NOT_IN_CAT");
		srbException.setProperty("-3119",
				"TOKEN_ALREADY_IN_CAT TOKEN_ALREADY_IN_CAT");
		srbException.setProperty("-3120", "INVALID_TICKET INVALID_TICKET");
		srbException.setProperty("-3121", "NAME2LONG_ERROR NAME2LONG_ERROR");
		srbException
				.setProperty("-3151",
						"MDAS_CATALOG_MODIFY_TYPE_ERROR MDAS_CATALOG_MODIFY_TYPE_ERROR");
		srbException.setProperty("-3152",
				"MDAS_CATALOG_TYPE_ERROR MDAS_CATALOG_TYPE_ERROR");
		srbException
				.setProperty("-3153", "UNIX_PUTENV_ERROR UNIX_PUTENV_ERROR");
		srbException.setProperty("-3154",
				"DATA_VALUE_TYPE_ERROR DATA_VALUE_TYPE_ERROR");
		srbException.setProperty("-3155",
				"INIT_ENVIRON_ERROR INIT_ENVIRON_ERROR");
		srbException.setProperty("-3156",
				"MDAS_CONFIG_FILE_ERROR MDAS_CONFIG_FILE_ERROR");
		srbException.setProperty("-3157",
				"MDAS_CATALOG_DIR_INFO_ERROR MDAS_CATALOG_DIR_INFO_ERROR");
		srbException.setProperty("-3158",
				"USER_PATH_NAME_EXPECTED USER_PATH_NAME_EXPECTED");
		srbException.setProperty("-3159",
				"NOT_A_SERVER_FUNCTION NOT_A_SERVER_FUNCTION");
		srbException.setProperty("-3201", "DATA_NOT_IN_CAT DATA_NOT_IN_CAT");
		srbException.setProperty("-3202", "DATA_NOT_IN_DOMN DATA_NOT_IN_DOMN");
		srbException.setProperty("-3203", "USER_NOT_IN_DOMN USER_NOT_IN_DOMN");
		srbException.setProperty("-3204",
				"NO_DATA_ACCS_TO_USER NO_DATA_ACCS_TO_USER");
		srbException.setProperty("-3205",
				"NO_DATA_ACCS_TO_USER_IN_DOMN NO_DATA_ACCS_TO_USER_IN_DOMN");
		srbException.setProperty("-3206",
				"USER_AUTHORIZATION_ERROR USER_AUTHORIZATION_ERROR");
		srbException.setProperty("-3207",
				"SYS_USER_AUTHORIZATION_ERROR SYS_USER_AUTHORIZATION_ERROR");
		srbException.setProperty("-3208", "USER_NOT_IN_CAT USER_NOT_IN_CAT");
		srbException.setProperty("-3209",
				"NO_DATA_REPLICA_IN_CAT NO_DATA_REPLICA_IN_CAT");
		srbException.setProperty("-3210",
				"DATA_NOT_UNIQUE_IN_COLLECTION DATA_NOT_UNIQUE_IN_COLLECTION");
		srbException.setProperty("-3211",
				"USER_GROUP_NOT_IN_CAT USER_GROUP_NOT_IN_CAT");
		srbException.setProperty("-3212",
				"USER_NOT_UNIQUE_IN_CAT USER_NOT_UNIQUE_IN_CAT");
		srbException.setProperty("-3213",
				"DATA_NOT_UNIQUE_IN_PATH DATA_NOT_UNIQUE_IN_PATH");
		srbException.setProperty("-3214",
				"MDAS_OBJ_NOT_IN_DOMN MDAS_OBJ_NOT_IN_DOMN");
		srbException.setProperty("-3215",
				"RESOURCE_NOT_IN_CAT RESOURCE_NOT_IN_CAT");
		srbException.setProperty("-3216",
				"RESOURCE_NOT_IN_DOMAIN RESOURCE_NOT_IN_DOMAIN");
		srbException.setProperty("-3217",
				"DATA_NOT_IN_RESOURCE_OR_PATH DATA_NOT_IN_RESOURCE_OR_PATH");
		srbException.setProperty("-3218",
				"USER_NOT_UNIQUE_IN_DOMN USER_NOT_UNIQUE_IN_DOMN");
		srbException.setProperty("-3219",
				"DATA_SUBCOLLECTION_NOT_UNIQUE DATA_SUBCOLLECTION_NOT_UNIQUE");
		srbException.setProperty("-3220",
				"NO_ACCS_TO_USER_IN_COLLECTION NO_ACCS_TO_USER_IN_COLLECTION");
		srbException.setProperty("-3221",
				"COLLECTION_NOT_IN_CAT COLLECTION_NOT_IN_CAT");
		srbException.setProperty("-3222",
				"DATA_NOT_IN_COLLECTION DATA_NOT_IN_COLLECTION");
		srbException.setProperty("-3223",
				"NO_MODIFY_DATA_ACCS_TO_USER NO_MODIFY_DATA_ACCS_TO_USER");
		srbException.setProperty("-3224",
				"DATA_TYPE_NOT_IN_CAT DATA_TYPE_NOT_IN_CAT");
		srbException
				.setProperty("-3225", "DOMAIN_NOT_IN_CAT DOMAIN_NOT_IN_CAT");
		srbException.setProperty("-3226",
				"ACCESS_TYPE_NOT_IN_CAT ACCESS_TYPE_NOT_IN_CAT");
		srbException.setProperty("-3227",
				"USER_TYPE_NOT_IN_CAT USER_TYPE_NOT_IN_CAT");
		srbException.setProperty("-3228",
				"ACTION_TYPE_NOT_IN_CAT ACTION_TYPE_NOT_IN_CAT");
		srbException.setProperty("-3229",
				"COLLECTION_ALREADY_IN_CAT COLLECTION_ALREADY_IN_CAT");
		srbException.setProperty("-3230",
				"COLLECTION_NOT_EMPTY COLLECTION_NOT_EMPTY");
		srbException.setProperty("-3231",
				"RESOURCE_ALREADY_IN_CAT RESOURCE_ALREADY_IN_CAT");
		srbException
				.setProperty("-3232",
						"LOGICAL_RESOURCE_ALREADY_IN_CAT LOGICAL_RESOURCE_ALREADY_IN_CAT");
		srbException.setProperty("-3233",
				"LOGICAL_RESOURCE_NOT_IN_CAT LOGICAL_RESOURCE_NOT_IN_CAT");
		srbException
				.setProperty("-3234",
						"REGISTRATION_PERMITS_INADEQUATE REGISTRATION_PERMITS_INADEQUATE");
		srbException.setProperty("-3235",
				"ERROR_IN_GIVEN_PATH ERROR_IN_GIVEN_PATH");
		srbException.setProperty("-3236",
				"ERROR_IN_DEFAULT_PATH ERROR_IN_DEFAULT_PATH");
		srbException.setProperty("-3237",
				"ERROR_IN_QUERY_CONDITION ERROR_IN_QUERY_CONDITION");
		srbException.setProperty("-3238",
				"NO_DATA_ACCS_TO_TICKET NO_DATA_ACCS_TO_TICKET");
		srbException.setProperty("-3239",
				"ATTRIBUTE_VALUE_REQUIRED ATTRIBUTE_VALUE_REQUIRED");
		srbException.setProperty("-3240",
				"ERROR_IN_SQL_GENERATION ERROR_IN_SQL_GENERATION");
		srbException.setProperty("-3241",
				"INCOMPATIBLE_ATTRIBUTE_TYPE INCOMPATIBLE_ATTRIBUTE_TYPE");
		srbException.setProperty("-3242",
				"NONUNIQUE_METADATA_ERROR NONUNIQUE_METADATA_ERROR");
		srbException.setProperty("-3243", "NO_VALUE_FOUND NO_VALUE_FOUND");
		srbException.setProperty("-3244",
				"DATE_ATTRIBUTE_VALUE_REQUIRED DATE_ATTRIBUTE_VALUE_REQUIRED");
		srbException
				.setProperty("-3245",
						"RANDOM_ATTRIBUTE_VALUE_REQUIRED RANDOM_ATTRIBUTE_VALUE_REQUIRED");
		srbException
				.setProperty("-3246",
						"PARENT_ATTRIBUTE_VALUE_REQUIRED PARENT_ATTRIBUTE_VALUE_REQUIRED");
		srbException.setProperty("-3247",
				"UNKNOWN_ATTRIBUTE_ID UNKNOWN_ATTRIBUTE_ID");
		srbException.setProperty("-3248",
				"UNKNOWN_USER_SCHEMASET UNKNOWN_USER_SCHEMASET");
		srbException.setProperty("-3249",
				"UNKNOWN_SCHEMA_NAME UNKNOWN_SCHEMA_NAME");
		srbException.setProperty("-3250",
				"SIZE_OF_ATTRIBUTE_UNKNOWN SIZE_OF_ATTRIBUTE_UNKNOWN");
		srbException
				.setProperty("-3251",
						"UNABLE_TO_MAKE_UNIQUE_ATTRIBUTE_NAME UNABLE_TO_MAKE_UNIQUE_ATTRIBUTE_NAME");
		srbException
				.setProperty("-3252",
						"UNABLE_TO_MAKE_UNIQUE_TABLE_NAME UNABLE_TO_MAKE_UNIQUE_TABLE_NAME");
		srbException.setProperty("-3253",
				"ATTRIBUTE_ID_COUNTER_ERROR ATTRIBUTE_ID_COUNTER_ERROR");
		srbException.setProperty("-3254",
				"TABLE_ID_COUNTER_ERROR TABLE_ID_COUNTER_ERROR");
		srbException
				.setProperty("-3255",
						"INSERT_INTO_NONLOCAL_MCAT_DATABASE INSERT_INTO_NONLOCAL_MCAT_DATABASE");
		srbException.setProperty("-3256",
				"SCHEMA_NAME_NOT_UNIQUE_IN_CAT SCHEMA_NAME_NOT_UNIQUE_IN_CAT");
		srbException
				.setProperty("-3257",
						"TABLE_METADATA_INSERTION_ERROR TABLE_METADATA_INSERTION_ERROR");
		srbException
				.setProperty("-3258",
						"ATTRIBUTE_METADATA_INSERTION_ERROR ATTRIBUTE_METADATA_INSERTION_ERROR");
		srbException.setProperty("-3259",
				"MCAT_TABLE_CREATION_ERROR MCAT_TABLE_CREATION_ERROR");
		srbException.setProperty("-3260",
				"MCAT_SCHEMA_CREATION_ERROR MCAT_SCHEMA_CREATION_ERROR");
		srbException.setProperty("-3261",
				"COLUMN_COUNT_MISMATCH COLUMN_COUNT_MISMATCH");
		srbException.setProperty("-3262",
				"INVALID_TOKEN_NAME INVALID_TOKEN_NAME");
		srbException.setProperty("-3263",
				"TOKEN_IDEN_TYPE_ERROR TOKEN_IDEN_TYPE_ERROR");
		srbException
				.setProperty("-3264",
						"FKREL_METADATA_INSERTION_ERROR FKREL_METADATA_INSERTION_ERROR");
		srbException.setProperty("-3265",
				"ACTION_NOT_ALLOWED_FOR_USER ACTION_NOT_ALLOWED_FOR_USER");
		srbException
				.setProperty("-3266", "DELETE_ATTR_ERROR DELETE_ATTR_ERROR");
		srbException.setProperty("-3267",
				"DELETE_SCHEMA_ERROR DELETE_SCHEMA_ERROR");
		srbException
				.setProperty("-3268", "INSERT_ATTR_ERROR INSERT_ATTR_ERROR");
		srbException
				.setProperty("-3269",
						"INNER_CORE_SCHEMA_CANNOT_BE_DELETED INNER_CORE_SCHEMA_CANNOT_BE_DELETED");
		srbException.setProperty("-3270",
				"ATTRIBUTE_SET_EMPTY ATTRIBUTE_SET_EMPTY");
		srbException.setProperty("-3271",
				"TEMP_TABLE_INSERTION_ERROR TEMP_TABLE_INSERTION_ERROR");
		srbException.setProperty("-3272",
				"TEMP_TABLE_CREATE_ERROR TEMP_TABLE_CREATE_ERROR");
		srbException.setProperty("-3273", "EXEC_SQL_FAILED EXEC_SQL_FAILED");
		srbException.setProperty("-3274",
				"GET_COLUMN_COUNT_FAILED GET_COLUMN_COUNT_FAILED");
		srbException.setProperty("-3275", "BIND_ROW_FAILED BIND_ROW_FAILED");
		srbException.setProperty("-3276", "GET_ROW_FAILED GET_ROW_FAILED");
		srbException.setProperty("-3277",
				"MISSING_PRIOR_CREATE MISSING_PRIOR_CREATE");
		srbException.setProperty("-3278",
				"ERROR_IN_QUERY_FORMATION ERROR_IN_QUERY_FORMATION");
		srbException.setProperty("-3279",
				"ERROR_IN_QUERY_UNKNOWN_QUERY ERROR_IN_QUERY_UNKNOWN_QUERY");
		srbException.setProperty("-3280", "ERROR_IN_QUERY ERROR_IN_QUERY");
		srbException
				.setProperty("-3281",
						"ERROR_IN_UPDATE_QUERY_FORMATION ERROR_IN_UPDATE_QUERY_FORMATION");
		srbException.setProperty("-3282",
				"UNKNOWN_FUNCTION_MAP_ID UNKNOWN_FUNCTION_MAP_ID");
		srbException.setProperty("-3283", "TABLE_LOCK_ERROR TABLE_LOCK_ERROR");
		srbException.setProperty("-3284",
				"NO_ACCS_TO_USER_IN_CONTAINER NO_ACCS_TO_USER_IN_CONTAINER");
		srbException
				.setProperty("-3285",
						"DATA_TYPE_COMPATIBILITY_FAILURE DATA_TYPE_COMPATIBILITY_FAILURE");
		srbException.setProperty("-3286",
				"CONTAINER_NOT_UNIQUE CONTAINER_NOT_UNIQUE");
		srbException.setProperty("-3287",
				"NOT_IN_CONTAINER_OBJECT NOT_IN_CONTAINER_OBJECT");
		srbException
				.setProperty(
						"-3288",
						"ACTION_NOT_ALLOWED_ON_IN_CONTAINER_OBJECT ACTION_NOT_ALLOWED_ON_IN_CONTAINER_OBJECT");
		srbException.setProperty("-3289",
				"FK_REL_TABLE_ID_ERROR FK_REL_TABLE_ID_ERROR");
		srbException.setProperty("-3290",
				"FOREIGN_REFERENCE_ERROR FOREIGN_REFERENCE_ERROR");
		srbException.setProperty("-3291",
				"CLUSTER_SCHEMA_EXISTS CLUSTER_SCHEMA_EXISTS");
		srbException
				.setProperty("-3292",
						"ACTION_NOT_ALLOWED_ON_CONTAINER_OBJECT ACTION_NOT_ALLOWED_ON_CONTAINER_OBJECT");
		srbException.setProperty("-3293",
				"ERROR_IN_CREATE_STRING ERROR_IN_CREATE_STRING");
		srbException.setProperty("-3294",
				"CONTAINER_NOT_IN_CAT CONTAINER_NOT_IN_CAT");
		srbException.setProperty("-3295",
				"CONTAINER_NOT_EMPTY CONTAINER_NOT_EMPTY");
		srbException.setProperty("-3296",
				"RESOURCE_CLASS_NOT_IN_CAT RESOURCE_CLASS_NOT_IN_CAT");
		srbException
				.setProperty("-3297",
						"DB_UNIQUE_CONSTRAINT_VIOLATION DB_UNIQUE_CONSTRAINT_VIOLATION");
		srbException.setProperty("-3298",
				"UNKNOWN_ATTRIBUTE_NAME UNKNOWN_ATTRIBUTE_NAME");
		srbException.setProperty("-3299",
				"METADATA_FORM_ERROR METADATA_FORM_ERROR");
		srbException.setProperty("-3300",
				"MDAS_AD_COMMENTS_APPEND_ERROR MDAS_AD_COMMENTS_APPEND_ERROR");
		srbException
				.setProperty("-3301",
						"NO_ACCS_TO_USER_IN_PHYSICAL_RESOURCE NO_ACCS_TO_USER_IN_PHYSICAL_RESOURCE");
		srbException.setProperty("-3302",
				"NO_MODIFY_RSRC_ACCS_TO_USER NO_MODIFY_RSRC_ACCS_TO_USER");
		srbException.setProperty("-3303",
				"ACTION_NOT_ALLOWED ACTION_NOT_ALLOWED");
		srbException.setProperty("-3304",
				"COLLECTION_NAME_ERROR COLLECTION_NAME_ERROR");
		srbException.setProperty("-3305",
				"COMPOUND_RESOURCE_NOT_IN_CAT COMPOUND_RESOURCE_NOT_IN_CAT");
		srbException.setProperty("-3306",
				"PHYSICAL_RESOURCE_NOT_IN_CAT PHYSICAL_RESOURCE_NOT_IN_CAT");
		srbException
				.setProperty("-3307",
						"ERROR_IN_COMPOUND_RESOURCE_NAME ERROR_IN_COMPOUND_RESOURCE_NAME");
		srbException
				.setProperty("-3308",
						"INTERNAL_COMPOUND_OBJECT_IS_NOT_UNIQUE INTERNAL_COMPOUND_OBJECT_IS_NOT_UNIQUE");
		srbException.setProperty("-3309",
				"COMPOUND_OBJECT_NOT_EMPTY COMPOUND_OBJECT_NOT_EMPTY");
		srbException
				.setProperty("-3310",
						"USER_HAS_INSUFFICIENT_PRIVILEGE USER_HAS_INSUFFICIENT_PRIVILEGE");
		srbException.setProperty("-3311",
				"DATA_PIN_OWNERSHIP_ERROR DATA_PIN_OWNERSHIP_ERROR");
		srbException.setProperty("-3312",
				"ZONE_NAME_ALREADY_EXISTS ZONE_NAME_ALREADY_EXISTS");
		srbException.setProperty("-3313",
				"LOCAL_ZONE_ALREADY_EXISTS LOCAL_ZONE_ALREADY_EXISTS");
		srbException.setProperty("-3314",
				"ZONE_NAME_NOT_IN_CAT ZONE_NAME_NOT_IN_CAT");
		srbException
				.setProperty(
						"-3315",
						"ACTN_NOT_ALLOWED_ON_IN_CONTAINER_OR_LINKED_OBJECT ACTION_NOT_ALLOWED_ON_IN_CONTAINER_OR_LINKED_OBJECT");
		srbException
				.setProperty("-3316",
						"OLD_EXTERNAL_GUID_EXISTS ACTION NOT ALLOWED AS OLD EXTERNAL GUID EXISTS");
		srbException.setProperty("-3317",
				"USER_NOT_CURATOR_ERROR USER_NOT_CURATOR_ERROR");
		srbException.setProperty("-3401",
				"MDAS_TD_LOCK_INSERTION_ERROR MDAS_TD_LOCK_INSERTION_ERROR");
		srbException.setProperty("-3402",
				"MDAS_TD_DOMN_INSERTION_ERROR MDAS_TD_DOMN_INSERTION_ERROR");
		srbException
				.setProperty("-3403",
						"MDAS_TD_DATA_GRP_INSERTION_ERROR MDAS_TD_DATA_GRP_INSERTION_ERROR");
		srbException
				.setProperty("-3404",
						"MDAS_TD_DATA_TYP_INSERTION_ERROR MDAS_TD_DATA_TYP_INSERTION_ERROR");
		srbException
				.setProperty("-3405",
						"MDAS_TD_METH_TYP_INSERTION_ERROR MDAS_TD_METH_TYP_INSERTION_ERROR");
		srbException
				.setProperty("-3406",
						"MDAS_TD_RSRC_TYP_INSERTION_ERROR MDAS_TD_RSRC_TYP_INSERTION_ERROR");
		srbException
				.setProperty("-3407",
						"MDAS_TD_USER_TYP_INSERTION_ERROR MDAS_TD_USER_TYP_INSERTION_ERROR");
		srbException
				.setProperty("-3408",
						"MDAS_TD_EXEC_TYP_INSERTION_ERROR MDAS_TD_EXEC_TYP_INSERTION_ERROR");
		srbException.setProperty("-3409",
				"MDAS_TD_LOCN_INSERTION_ERROR MDAS_TD_LOCN_INSERTION_ERROR");
		srbException
				.setProperty("-3410",
						"MDAS_TD_RSRC_FUNC_INSERTION_ERROR MDAS_TD_RSRC_FUNC_INSERTION_ERROR");
		srbException
				.setProperty("-3411",
						"MDAS_TD_DS_FLDTYP_INSERTION_ERROR MDAS_TD_DS_FLDTYP_INSERTION_ERROR");
		srbException
				.setProperty("-3412",
						"MDAS_TD_DS_SCHM_INSERTION_ERROR MDAS_TD_DS_SCHM_INSERTION_ERROR");
		srbException
				.setProperty("-3413",
						"MDAS_TD_DS_SCHMDSC_INSERTION_ERROR MDAS_TD_DS_SCHMDSC_INSERTION_ERROR");
		srbException
				.setProperty("-3414",
						"MDAS_TD_SPEC_SUMM_INSERTION_ERROR MDAS_TD_SPEC_SUMM_INSERTION_ERROR");
		srbException.setProperty("-3415",
				"MDAS_TD_ACTN_INSERTION_ERROR MDAS_TD_ACTN_INSERTION_ERROR");
		srbException.setProperty("-3416",
				"MDAS_TD_TCKT_INSERTION_ERROR MDAS_TD_TCKT_INSERTION_ERROR");
		srbException.setProperty("-3417",
				"MDAS_TD_AUTH_INSERTION_ERROR MDAS_TD_AUTH_INSERTION_ERROR");
		srbException.setProperty("-3418",
				"MDAS_TD_VERI_INSERTION_ERROR MDAS_TD_VERI_INSERTION_ERROR");
		srbException.setProperty("-3419",
				"MDAS_TD_ENCR_INSERTION_ERROR MDAS_TD_ENCR_INSERTION_ERROR");
		srbException.setProperty("-3420",
				"MDAS_TD_DECR_INSERTION_ERROR MDAS_TD_DECR_INSERTION_ERROR");
		srbException
				.setProperty("-3421",
						"MDAS_TD_RSRC_ACCS_INSERTION_ERROR MDAS_TD_RSRC_ACCS_INSERTION_ERROR");
		srbException
				.setProperty("-3422",
						"MDAS_TD_METH_ACCS_INSERTION_ERROR MDAS_TD_METH_ACCS_INSERTION_ERROR");
		srbException
				.setProperty("-3423",
						"MDAS_TD_DS_ACCS_INSERTION_ERROR MDAS_TD_DS_ACCS_INSERTION_ERROR");
		srbException.setProperty("-3424",
				"MDAS_CD_DATA_INSERTION_ERROR MDAS_CD_DATA_INSERTION_ERROR");
		srbException.setProperty("-3425",
				"MDAS_CD_METH_INSERTION_ERROR MDAS_CD_METH_INSERTION_ERROR");
		srbException.setProperty("-3426",
				"MDAS_CD_RSRC_INSERTION_ERROR MDAS_CD_RSRC_INSERTION_ERROR");
		srbException.setProperty("-3427",
				"MDAS_CD_USER_INSERTION_ERROR MDAS_CD_USER_INSERTION_ERROR");
		srbException
				.setProperty("-3428",
						"MDAS_TD_REPL_TYP_INSERTION_ERROR MDAS_TD_REPL_TYP_INSERTION_ERROR");
		srbException
				.setProperty("-3429",
						"MDAS_TD_DS_REPPLCY_INSERTION_ERROR MDAS_TD_DS_REPPLCY_INSERTION_ERROR");
		srbException
				.setProperty("-3430",
						"MDAS_TD_DS_PRTPLCY_INSERTION_ERROR MDAS_TD_DS_PRTPLCY_INSERTION_ERROR");
		srbException
				.setProperty("-3431",
						"MDAS_TD_DS_TRIG_INSERTION_ERROR MDAS_TD_DS_TRIG_INSERTION_ERROR");
		srbException
				.setProperty("-3432",
						"MDAS_TD_DS_AGGR_INSERTION_ERROR MDAS_TD_DS_AGGR_INSERTION_ERROR");
		srbException
				.setProperty("-3433",
						"MDAS_TD_MTHREP_PLC_INSERTION_ERROR MDAS_TD_MTHREP_PLC_INSERTION_ERROR");
		srbException
				.setProperty("-3434",
						"MDAS_TD_RSRREP_PLC_INSERTION_ERROR MDAS_TD_RSRREP_PLC_INSERTION_ERROR");
		srbException.setProperty("-3435",
				"MDAS_AD_ALIAS_INSERTION_ERROR MDAS_AD_ALIAS_INSERTION_ERROR");
		srbException.setProperty("-3436",
				"MDAS_AD_DOMN_INSERTION_ERROR MDAS_AD_DOMN_INSERTION_ERROR");
		srbException
				.setProperty("-3437",
						"MDAS_AD_AUTH_KEY_INSERTION_ERROR MDAS_AD_AUTH_KEY_INSERTION_ERROR");
		srbException.setProperty("-3438",
				"MDAS_AD_REPL_INSERTION_ERROR MDAS_AD_REPL_INSERTION_ERROR");
		srbException
				.setProperty("-3439",
						"MDAS_AD_COMMENTS_INSERTION_ERROR MDAS_AD_COMMENTS_INSERTION_ERROR");
		srbException.setProperty("-3440",
				"MDAS_AD_LOCK_INSERTION_ERROR MDAS_AD_LOCK_INSERTION_ERROR");
		srbException.setProperty("-3441",
				"MDAS_AD_ACCS_INSERTION_ERROR MDAS_AD_ACCS_INSERTION_ERROR");
		srbException.setProperty("-3442",
				"MDAS_AD_PART_INSERTION_ERROR MDAS_AD_PART_INSERTION_ERROR");
		srbException.setProperty("-3443",
				"MDAS_AD_AGGR_INSERTION_ERROR MDAS_AD_AGGR_INSERTION_ERROR");
		srbException.setProperty("-3444",
				"MDAS_AD_SUMM_INSERTION_ERROR MDAS_AD_SUMM_INSERTION_ERROR");
		srbException.setProperty("-3445",
				"MDAS_AD_TRIG_INSERTION_ERROR MDAS_AD_TRIG_INSERTION_ERROR");
		srbException.setProperty("-3446",
				"MDAS_AD_AUDIT_INSERTION_ERROR MDAS_AD_AUDIT_INSERTION_ERROR");
		srbException
				.setProperty("-3447",
						"MDAS_AD_LIN_DATA_INSERTION_ERROR MDAS_AD_LIN_DATA_INSERTION_ERROR");
		srbException
				.setProperty("-3448",
						"MDAS_AD_LIN_METH_INSERTION_ERROR MDAS_AD_LIN_METH_INSERTION_ERROR");
		srbException
				.setProperty("-3449",
						"MDAS_AD_LIN_USER_INSERTION_ERROR MDAS_AD_LIN_USER_INSERTION_ERROR");
		srbException
				.setProperty("-3450",
						"MDAS_AD_LIN_PARM_INSERTION_ERROR MDAS_AD_LIN_PARM_INSERTION_ERROR");
		srbException
				.setProperty("-3451",
						"MDAS_AD_LIN_RSRC_INSERTION_ERROR MDAS_AD_LIN_RSRC_INSERTION_ERROR");
		srbException
				.setProperty("-3452",
						"MDAS_AD_FON_DATA_INSERTION_ERROR MDAS_AD_FON_DATA_INSERTION_ERROR");
		srbException.setProperty("-3453",
				"MDAS_AD_SD_INSERTION_ERROR MDAS_AD_SD_INSERTION_ERROR");
		srbException
				.setProperty("-3454",
						"MDAS_AD_TYP_SD_INSERTION_ERROR MDAS_AD_TYP_SD_INSERTION_ERROR");
		srbException.setProperty("-3455",
				"MDAS_AM_ALIAS_INSERTION_ERROR MDAS_AM_ALIAS_INSERTION_ERROR");
		srbException.setProperty("-3456",
				"MDAS_AM_DOMN_INSERTION_ERROR MDAS_AM_DOMN_INSERTION_ERROR");
		srbException
				.setProperty("-3457",
						"MDAS_AM_AUTH_KEY_INSERTION_ERROR MDAS_AM_AUTH_KEY_INSERTION_ERROR");
		srbException
				.setProperty("-3458",
						"MDAS_AM_DECR_KEY_INSERTION_ERROR MDAS_AM_DECR_KEY_INSERTION_ERROR");
		srbException.setProperty("-3459",
				"MDAS_AM_REPL_INSERTION_ERROR MDAS_AM_REPL_INSERTION_ERROR");
		srbException
				.setProperty("-3460",
						"MDAS_AM_COMMENTS_INSERTION_ERROR MDAS_AM_COMMENTS_INSERTION_ERROR");
		srbException.setProperty("-3461",
				"MDAS_AM_LOCK_INSERTION_ERROR MDAS_AM_LOCK_INSERTION_ERROR");
		srbException.setProperty("-3462",
				"MDAS_AM_ACCS_INSERTION_ERROR MDAS_AM_ACCS_INSERTION_ERROR");
		srbException.setProperty("-3463",
				"MDAS_AM_SUMM_INSERTION_ERROR MDAS_AM_SUMM_INSERTION_ERROR");
		srbException.setProperty("-3464",
				"MDAS_AM_AUDIT_INSERTION_ERROR MDAS_AM_AUDIT_INSERTION_ERROR");
		srbException
				.setProperty("-3465",
						"MDAS_AM_LIN_METH_INSERTION_ERROR MDAS_AM_LIN_METH_INSERTION_ERROR");
		srbException
				.setProperty("-3466",
						"MDAS_AM_LIN_DATA_INSERTION_ERROR MDAS_AM_LIN_DATA_INSERTION_ERROR");
		srbException
				.setProperty("-3467",
						"MDAS_AM_LIN_USER_INSERTION_ERROR MDAS_AM_LIN_USER_INSERTION_ERROR");
		srbException
				.setProperty("-3468",
						"MDAS_AM_LIN_PARM_INSERTION_ERROR MDAS_AM_LIN_PARM_INSERTION_ERROR");
		srbException
				.setProperty("-3469",
						"MDAS_AM_LIN_RSRC_INSERTION_ERROR MDAS_AM_LIN_RSRC_INSERTION_ERROR");
		srbException
				.setProperty("-3470",
						"MDAS_AM_CNVT_MTHID_INSERTION_ERROR MDAS_AM_CNVT_MTHID_INSERTION_ERROR");
		srbException
				.setProperty("-3471",
						"MDAS_AM_CNVT_MTHTY_INSERTION_ERROR MDAS_AM_CNVT_MTHTY_INSERTION_ERROR");
		srbException
				.setProperty("-3472",
						"MDAS_AM_APPL_PARM_INSERTION_ERROR MDAS_AM_APPL_PARM_INSERTION_ERROR");
		srbException
				.setProperty("-3473",
						"MDAS_AM_APPL_OUT_INSERTION_ERROR MDAS_AM_APPL_OUT_INSERTION_ERROR");
		srbException
				.setProperty("-3474",
						"MDAS_AM_APPL_IN_INSERTION_ERROR MDAS_AM_APPL_IN_INSERTION_ERROR");
		srbException
				.setProperty("-3475",
						"MDAS_AM_APPL_RQR_INSERTION_ERROR MDAS_AM_APPL_RQR_INSERTION_ERROR");
		srbException
				.setProperty("-3476",
						"MDAS_AM_APPL_PRED_INSERTION_ERROR MDAS_AM_APPL_PRED_INSERTION_ERROR");
		srbException
				.setProperty("-3477",
						"MDAS_AM_COMP_MAP_INSERTION_ERROR MDAS_AM_COMP_MAP_INSERTION_ERROR");
		srbException
				.setProperty("-3478",
						"MDAS_AM_COMP_MAPDS_INSERTION_ERROR MDAS_AM_COMP_MAPDS_INSERTION_ERROR");
		srbException
				.setProperty("-3479",
						"MDAS_AM_COMP_MAPPR_INSERTION_ERROR MDAS_AM_COMP_MAPPR_INSERTION_ERROR");
		srbException.setProperty("-3480",
				"MDAS_AM_SD_INSERTION_ERROR MDAS_AM_SD_INSERTION_ERROR");
		srbException
				.setProperty("-3481",
						"MDAS_AM_TYP_SD_INSERTION_ERROR MDAS_AM_TYP_SD_INSERTION_ERROR");
		srbException.setProperty("-3482",
				"MDAS_AR_ALIAS_INSERTION_ERROR MDAS_AR_ALIAS_INSERTION_ERROR");
		srbException.setProperty("-3483",
				"MDAS_AR_DOMN_INSERTION_ERROR MDAS_AR_DOMN_INSERTION_ERROR");
		srbException
				.setProperty("-3484",
						"MDAS_AR_AUTH_KEY_INSERTION_ERROR MDAS_AR_AUTH_KEY_INSERTION_ERROR");
		srbException
				.setProperty("-3485",
						"MDAS_AR_DECR_KEY_INSERTION_ERROR MDAS_AR_DECR_KEY_INSERTION_ERROR");
		srbException.setProperty("-3486",
				"MDAS_AR_REPL_INSERTION_ERROR MDAS_AR_REPL_INSERTION_ERROR");
		srbException
				.setProperty("-3487",
						"MDAS_AR_COMMENTS_INSERTION_ERROR MDAS_AR_COMMENTS_INSERTION_ERROR");
		srbException.setProperty("-3488",
				"MDAS_AR_LOCK_INSERTION_ERROR MDAS_AR_LOCK_INSERTION_ERROR");
		srbException.setProperty("-3489",
				"MDAS_AR_ACCS_INSERTION_ERROR MDAS_AR_ACCS_INSERTION_ERROR");
		srbException.setProperty("-3490",
				"MDAS_AR_SUMM_INSERTION_ERROR MDAS_AR_SUMM_INSERTION_ERROR");
		srbException.setProperty("-3491",
				"MDAS_AR_AUDIT_INSERTION_ERROR MDAS_AR_AUDIT_INSERTION_ERROR");
		srbException
				.setProperty("-3492",
						"MDAS_AR_LIN_RSRC_INSERTION_ERROR MDAS_AR_LIN_RSRC_INSERTION_ERROR");
		srbException
				.setProperty("-3493",
						"MDAS_AR_LIN_METH_INSERTION_ERROR MDAS_AR_LIN_METH_INSERTION_ERROR");
		srbException
				.setProperty("-3494",
						"MDAS_AR_LIN_USER_INSERTION_ERROR MDAS_AR_LIN_USER_INSERTION_ERROR");
		srbException
				.setProperty("-3495",
						"MDAS_AR_LIN_PARM_INSERTION_ERROR MDAS_AR_LIN_PARM_INSERTION_ERROR");
		srbException
				.setProperty("-3496",
						"MDAS_AR_LIN_DATA_INSERTION_ERROR MDAS_AR_LIN_DATA_INSERTION_ERROR");
		srbException
				.setProperty("-3497",
						"MDAS_AR_FON_RSRC_INSERTION_ERROR MDAS_AR_FON_RSRC_INSERTION_ERROR");
		srbException
				.setProperty("-3498",
						"MDAS_AR_APPL_PRED_INSERTION_ERROR MDAS_AR_APPL_PRED_INSERTION_ERROR");
		srbException.setProperty("-3499",
				"MDAS_AR_SD_INSERTION_ERROR MDAS_AR_SD_INSERTION_ERROR");
		srbException
				.setProperty("-3500",
						"MDAS_AR_TYP_SD_INSERTION_ERROR MDAS_AR_TYP_SD_INSERTION_ERROR");
		srbException.setProperty("-3501",
				"MDAS_AU_INFO_INSERTION_ERROR MDAS_AU_INFO_INSERTION_ERROR");
		srbException.setProperty("-3502",
				"MDAS_AU_ALIAS_INSERTION_ERROR MDAS_AU_ALIAS_INSERTION_ERROR");
		srbException.setProperty("-3503",
				"MDAS_AU_GROUP_INSERTION_ERROR MDAS_AU_GROUP_INSERTION_ERROR");
		srbException.setProperty("-3504",
				"MDAS_AU_DOMN_INSERTION_ERROR MDAS_AU_DOMN_INSERTION_ERROR");
		srbException
				.setProperty("-3505",
						"MDAS_AU_AUTH_KEY_INSERTION_ERROR MDAS_AU_AUTH_KEY_INSERTION_ERROR");
		srbException
				.setProperty("-3506",
						"MDAS_AU_DECR_KEY_INSERTION_ERROR MDAS_AU_DECR_KEY_INSERTION_ERROR");
		srbException.setProperty("-3507",
				"MDAS_AU_SUMM_INSERTION_ERROR MDAS_AU_SUMM_INSERTION_ERROR");
		srbException.setProperty("-3508",
				"MDAS_AU_AUDIT_INSERTION_ERROR MDAS_AU_AUDIT_INSERTION_ERROR");
		srbException.setProperty("-3509",
				"MDAS_AU_SD_INSERTION_ERROR MDAS_AU_SD_INSERTION_ERROR");
		srbException
				.setProperty("-3510",
						"MDAS_AU_TYP_SD_INSERTION_ERROR MDAS_AU_TYP_SD_INSERTION_ERROR");
		srbException.setProperty("-3511",
				"MDAS_COUNTER_INSERTION_ERROR MDAS_COUNTER_INSERTION_ERROR");
		srbException
				.setProperty("-3512",
						"MDAS_SD_KEYWORD_INSERTION_ERROR MDAS_SD_KEYWORD_INSERTION_ERROR");
		srbException
				.setProperty("-3513",
						"MDAS_TD_DATA_ACCS_INSERTION_ERROR MDAS_TD_DATA_ACCS_INSERTION_ERROR");
		srbException
				.setProperty("-3514",
						"MDAS_AD_ACCESS_INSERTION_ERROR MDAS_AD_ACCESS_INSERTION_ERROR");
		srbException
				.setProperty("-3515",
						"MDAS_AD_GRP_ACCS_INSERTION_ERROR MDAS_AD_GRP_ACCS_INSERTION_ERROR");
		srbException
				.setProperty("-3516",
						"MDAS_AR_PHYSICAL_INSERTION_ERROR MDAS_AR_PHYSICAL_INSERTION_ERROR");
		srbException
				.setProperty("-3517",
						"MDAS_AU_TCKT_DATA_INSERTION_ERROR MDAS_AU_TCKT_DATA_INSERTION_ERROR");
		srbException
				.setProperty("-3518",
						"MDAS_AU_TCKT_GRP_INSERTION_ERROR MDAS_AU_TCKT_GRP_INSERTION_ERROR");
		srbException
				.setProperty("-3519",
						"MDAS_AR_DB_RSRC_INSERTION_ERROR MDAS_AR_DB_RSRC_INSERTION_ERROR");
		srbException
				.setProperty("-3520",
						"MDAS_TD_CONTAINER_INSERTION_ERROR MDAS_TD_CONTAINER_INSERTION_ERROR");
		srbException
				.setProperty("-3521",
						"MDAS_TD_RSRC_CLASS_INSERTION_ERROR MDAS_TD_RSRC_CLASS_INSERTION_ERROR");
		srbException
				.setProperty("-3522",
						"MDAS_AU_AUTH_MAP_INSERTION_ERROR MDAS_AU_AUTH_MAP_INSERTION_ERROR");
		srbException.setProperty("-3523",
				"MDAS_AR_AUTH_INSERTION_ERROR MDAS_AR_AUTH_INSERTION_ERROR");
		srbException
				.setProperty("-3524",
						"MDAS_TD_AUTH_SCHM_INSERTION_ERROR MDAS_TD_AUTH_SCHM_INSERTION_ERROR");
		srbException
				.setProperty("-3525",
						"MDAS_AD_ANNOTATION_INSERTION_ERROR MDAS_AD_ANNOTATION_INSERTION_ERROR");
		srbException.setProperty("-3526",
				"MDAS_AD_CLASS_INSERTION_ERROR MDAS_AD_CLASS_INSERTION_ERROR");
		srbException
				.setProperty("-3527",
						"MDAS_AD_DATA_INDEX_INSERTION_ERROR MDAS_AD_DATA_INDEX_INSERTION_ERROR");
		srbException
				.setProperty("-3528",
						"MDAS_AD_DTYP_INDEX_INSERTION_ERROR MDAS_AD_DTYP_INDEX_INSERTION_ERROR");
		srbException
				.setProperty("-3529",
						"MDAS_AD_COLL_INDEX_INSERTION_ERROR MDAS_AD_COLL_INDEX_INSERTION_ERROR");
		srbException
				.setProperty("-3530",
						"MDAS_AD_DATA_METH_INSERTION_ERROR MDAS_AD_DATA_METH_INSERTION_ERROR");
		srbException
				.setProperty("-3531",
						"MDAS_AD_DTYP_METH_INSERTION_ERROR MDAS_AD_DTYP_METH_INSERTION_ERROR");
		srbException
				.setProperty("-3532",
						"MDAS_AD_COLL_METH_INSERTION_ERROR MDAS_AD_COLL_METH_INSERTION_ERROR");
		srbException
				.setProperty("-3533",
						"MDAS_AD_STRCT_BLOB_INSERTION_ERROR MDAS_AD_STRCT_BLOB_INSERTION_ERROR");
		srbException.setProperty("-3534",
				"MDAS_AD_MDATA_INSERTION_ERROR MDAS_AD_MDATA_INSERTION_ERROR");
		srbException
				.setProperty("-3535",
						"MDAS_AD_COLLCONT_INSERTION_ERROR MDAS_AD_COLLCONT_INSERTION_ERROR");
		srbException
				.setProperty("-3536",
						"MDAS_AD_COLLMDATA_INSERTION_ERROR MDAS_AD_COLLMDATA_INSERTION_ERROR");
		srbException
				.setProperty("-3537",
						"MDAS_AC_ANNOTATION_INSERTION_ERROR MDAS_AC_ANNOTATION_INSERTION_ERROR");
		srbException
				.setProperty("-3538",
						"MDAS_AR_CMPND_RSRC_INSERTION_ERROR MDAS_AR_CMPND_RSRC_INSERTION_ERROR");
		srbException
				.setProperty("-3539",
						"MDAS_AD_COMPOUND_INSERTION_ERROR MDAS_AD_COMPOUND_INSERTION_ERROR");
		srbException
				.setProperty("-3540",
						"MDAS_AR_TAPE_INFO_INSERTION_ERROR MDAS_AR_TAPE_INFO_INSERTION_ERROR");
		srbException.setProperty("-3541",
				"MDAS_AD_MISC1_INSERTION_ERROR MDAS_AD_MISC1_INSERTION_ERROR");
		srbException.setProperty("-3542",
				"MDAS_AU_MDATA_INSERTION_ERROR MDAS_AU_MDATA_INSERTION_ERROR");
		srbException.setProperty("-3543",
				"MDAS_AR_MDATA_INSERTION_ERROR MDAS_AR_MDATA_INSERTION_ERROR");
		srbException.setProperty("-3544",
				"MDAS_TD_ZONE_INSERTION_ERROR MDAS_TD_ZONE_INSERTION_ERROR");
		srbException.setProperty("-3545",
				"MDAS_AD_GUID_INSERTION_ERROR MDAS_AD_GUID_INSERTION_ERROR");
		srbException.setProperty("-3546",
				"MDAS_AR_INFO_INSERTION_ERROR MDAS_AR_INFO_INSERTION_ERROR");
		srbException
				.setProperty("-3547",
						"MDAS_AR_DAILY_TOT_INSERTION_ERROR MDAS_AR_DAILY_TOT_INSERTION_ERROR");
		srbException
				.setProperty("-3548",
						"MDAS_AR_USE_QUOTA_INSERTION_ERROR MDAS_AR_USE_QUOTA_INSERTION_ERROR");
		srbException.setProperty("-3601",
				"MDAS_TD_LOCK_DELETION_ERROR MDAS_TD_LOCK_DELETION_ERROR");
		srbException.setProperty("-3602",
				"MDAS_TD_DOMN_DELETION_ERROR MDAS_TD_DOMN_DELETION_ERROR");
		srbException
				.setProperty("-3603",
						"MDAS_TD_DATA_GRP_DELETION_ERROR MDAS_TD_DATA_GRP_DELETION_ERROR");
		srbException
				.setProperty("-3604",
						"MDAS_TD_DATA_TYP_DELETION_ERROR MDAS_TD_DATA_TYP_DELETION_ERROR");
		srbException
				.setProperty("-3605",
						"MDAS_TD_METH_TYP_DELETION_ERROR MDAS_TD_METH_TYP_DELETION_ERROR");
		srbException
				.setProperty("-3606",
						"MDAS_TD_RSRC_TYP_DELETION_ERROR MDAS_TD_RSRC_TYP_DELETION_ERROR");
		srbException
				.setProperty("-3607",
						"MDAS_TD_USER_TYP_DELETION_ERROR MDAS_TD_USER_TYP_DELETION_ERROR");
		srbException
				.setProperty("-3608",
						"MDAS_TD_EXEC_TYP_DELETION_ERROR MDAS_TD_EXEC_TYP_DELETION_ERROR");
		srbException.setProperty("-3609",
				"MDAS_TD_LOCN_DELETION_ERROR MDAS_TD_LOCN_DELETION_ERROR");
		srbException
				.setProperty("-3610",
						"MDAS_TD_RSRC_FUNC_DELETION_ERROR MDAS_TD_RSRC_FUNC_DELETION_ERROR");
		srbException
				.setProperty("-3611",
						"MDAS_TD_DS_FLDTYP_DELETION_ERROR MDAS_TD_DS_FLDTYP_DELETION_ERROR");
		srbException
				.setProperty("-3612",
						"MDAS_TD_DS_SCHM_DELETION_ERROR MDAS_TD_DS_SCHM_DELETION_ERROR");
		srbException
				.setProperty("-3613",
						"MDAS_TD_DS_SCHMDSC_DELETION_ERROR MDAS_TD_DS_SCHMDSC_DELETION_ERROR");
		srbException
				.setProperty("-3614",
						"MDAS_TD_SPEC_SUMM_DELETION_ERROR MDAS_TD_SPEC_SUMM_DELETION_ERROR");
		srbException.setProperty("-3615",
				"MDAS_TD_ACTN_DELETION_ERROR MDAS_TD_ACTN_DELETION_ERROR");
		srbException.setProperty("-3616",
				"MDAS_TD_TCKT_DELETION_ERROR MDAS_TD_TCKT_DELETION_ERROR");
		srbException.setProperty("-3617",
				"MDAS_TD_AUTH_DELETION_ERROR MDAS_TD_AUTH_DELETION_ERROR");
		srbException.setProperty("-3618",
				"MDAS_TD_VERI_DELETION_ERROR MDAS_TD_VERI_DELETION_ERROR");
		srbException.setProperty("-3619",
				"MDAS_TD_ENCR_DELETION_ERROR MDAS_TD_ENCR_DELETION_ERROR");
		srbException.setProperty("-3620",
				"MDAS_TD_DECR_DELETION_ERROR MDAS_TD_DECR_DELETION_ERROR");
		srbException
				.setProperty("-3621",
						"MDAS_TD_RSRC_ACCS_DELETION_ERROR MDAS_TD_RSRC_ACCS_DELETION_ERROR");
		srbException
				.setProperty("-3622",
						"MDAS_TD_METH_ACCS_DELETION_ERROR MDAS_TD_METH_ACCS_DELETION_ERROR");
		srbException
				.setProperty("-3623",
						"MDAS_TD_DS_ACCS_DELETION_ERROR MDAS_TD_DS_ACCS_DELETION_ERROR");
		srbException.setProperty("-3624",
				"MDAS_CD_DATA_DELETION_ERROR MDAS_CD_DATA_DELETION_ERROR");
		srbException.setProperty("-3625",
				"MDAS_CD_METH_DELETION_ERROR MDAS_CD_METH_DELETION_ERROR");
		srbException.setProperty("-3626",
				"MDAS_CD_RSRC_DELETION_ERROR MDAS_CD_RSRC_DELETION_ERROR");
		srbException.setProperty("-3627",
				"MDAS_CD_USER_DELETION_ERROR MDAS_CD_USER_DELETION_ERROR");
		srbException
				.setProperty("-3628",
						"MDAS_TD_REPL_TYP_DELETION_ERROR MDAS_TD_REPL_TYP_DELETION_ERROR");
		srbException
				.setProperty("-3629",
						"MDAS_TD_DS_REPPLCY_DELETION_ERROR MDAS_TD_DS_REPPLCY_DELETION_ERROR");
		srbException
				.setProperty("-3630",
						"MDAS_TD_DS_PRTPLCY_DELETION_ERROR MDAS_TD_DS_PRTPLCY_DELETION_ERROR");
		srbException
				.setProperty("-3631",
						"MDAS_TD_DS_TRIG_DELETION_ERROR MDAS_TD_DS_TRIG_DELETION_ERROR");
		srbException
				.setProperty("-3632",
						"MDAS_TD_DS_AGGR_DELETION_ERROR MDAS_TD_DS_AGGR_DELETION_ERROR");
		srbException
				.setProperty("-3633",
						"MDAS_TD_MTHREP_PLC_DELETION_ERROR MDAS_TD_MTHREP_PLC_DELETION_ERROR");
		srbException
				.setProperty("-3634",
						"MDAS_TD_RSRREP_PLC_DELETION_ERROR MDAS_TD_RSRREP_PLC_DELETION_ERROR");
		srbException.setProperty("-3635",
				"MDAS_AD_ALIAS_DELETION_ERROR MDAS_AD_ALIAS_DELETION_ERROR");
		srbException.setProperty("-3636",
				"MDAS_AD_DOMN_DELETION_ERROR MDAS_AD_DOMN_DELETION_ERROR");
		srbException
				.setProperty("-3637",
						"MDAS_AD_AUTH_KEY_DELETION_ERROR MDAS_AD_AUTH_KEY_DELETION_ERROR");
		srbException.setProperty("-3638",
				"MDAS_AD_REPL_DELETION_ERROR MDAS_AD_REPL_DELETION_ERROR");
		srbException
				.setProperty("-3639",
						"MDAS_AD_COMMENTS_DELETION_ERROR MDAS_AD_COMMENTS_DELETION_ERROR");
		srbException.setProperty("-3640",
				"MDAS_AD_LOCK_DELETION_ERROR MDAS_AD_LOCK_DELETION_ERROR");
		srbException.setProperty("-3641",
				"MDAS_AD_ACCS_DELETION_ERROR MDAS_AD_ACCS_DELETION_ERROR");
		srbException.setProperty("-3642",
				"MDAS_AD_PART_DELETION_ERROR MDAS_AD_PART_DELETION_ERROR");
		srbException.setProperty("-3643",
				"MDAS_AD_AGGR_DELETION_ERROR MDAS_AD_AGGR_DELETION_ERROR");
		srbException.setProperty("-3644",
				"MDAS_AD_SUMM_DELETION_ERROR MDAS_AD_SUMM_DELETION_ERROR");
		srbException.setProperty("-3645",
				"MDAS_AD_TRIG_DELETION_ERROR MDAS_AD_TRIG_DELETION_ERROR");
		srbException.setProperty("-3646",
				"MDAS_AD_AUDIT_DELETION_ERROR MDAS_AD_AUDIT_DELETION_ERROR");
		srbException
				.setProperty("-3647",
						"MDAS_AD_LIN_DATA_DELETION_ERROR MDAS_AD_LIN_DATA_DELETION_ERROR");
		srbException
				.setProperty("-3648",
						"MDAS_AD_LIN_METH_DELETION_ERROR MDAS_AD_LIN_METH_DELETION_ERROR");
		srbException
				.setProperty("-3649",
						"MDAS_AD_LIN_USER_DELETION_ERROR MDAS_AD_LIN_USER_DELETION_ERROR");
		srbException
				.setProperty("-3650",
						"MDAS_AD_LIN_PARM_DELETION_ERROR MDAS_AD_LIN_PARM_DELETION_ERROR");
		srbException
				.setProperty("-3651",
						"MDAS_AD_LIN_RSRC_DELETION_ERROR MDAS_AD_LIN_RSRC_DELETION_ERROR");
		srbException
				.setProperty("-3652",
						"MDAS_AD_FON_DATA_DELETION_ERROR MDAS_AD_FON_DATA_DELETION_ERROR");
		srbException.setProperty("-3653",
				"MDAS_AD_SD_DELETION_ERROR MDAS_AD_SD_DELETION_ERROR");
		srbException.setProperty("-3654",
				"MDAS_AD_TYP_SD_DELETION_ERROR MDAS_AD_TYP_SD_DELETION_ERROR");
		srbException.setProperty("-3655",
				"MDAS_AM_ALIAS_DELETION_ERROR MDAS_AM_ALIAS_DELETION_ERROR");
		srbException.setProperty("-3656",
				"MDAS_AM_DOMN_DELETION_ERROR MDAS_AM_DOMN_DELETION_ERROR");
		srbException
				.setProperty("-3657",
						"MDAS_AM_AUTH_KEY_DELETION_ERROR MDAS_AM_AUTH_KEY_DELETION_ERROR");
		srbException
				.setProperty("-3658",
						"MDAS_AM_DECR_KEY_DELETION_ERROR MDAS_AM_DECR_KEY_DELETION_ERROR");
		srbException.setProperty("-3659",
				"MDAS_AM_REPL_DELETION_ERROR MDAS_AM_REPL_DELETION_ERROR");
		srbException
				.setProperty("-3660",
						"MDAS_AM_COMMENTS_DELETION_ERROR MDAS_AM_COMMENTS_DELETION_ERROR");
		srbException.setProperty("-3661",
				"MDAS_AM_LOCK_DELETION_ERROR MDAS_AM_LOCK_DELETION_ERROR");
		srbException.setProperty("-3662",
				"MDAS_AM_ACCS_DELETION_ERROR MDAS_AM_ACCS_DELETION_ERROR");
		srbException.setProperty("-3663",
				"MDAS_AM_SUMM_DELETION_ERROR MDAS_AM_SUMM_DELETION_ERROR");
		srbException.setProperty("-3664",
				"MDAS_AM_AUDIT_DELETION_ERROR MDAS_AM_AUDIT_DELETION_ERROR");
		srbException
				.setProperty("-3665",
						"MDAS_AM_LIN_METH_DELETION_ERROR MDAS_AM_LIN_METH_DELETION_ERROR");
		srbException
				.setProperty("-3666",
						"MDAS_AM_LIN_DATA_DELETION_ERROR MDAS_AM_LIN_DATA_DELETION_ERROR");
		srbException
				.setProperty("-3667",
						"MDAS_AM_LIN_USER_DELETION_ERROR MDAS_AM_LIN_USER_DELETION_ERROR");
		srbException
				.setProperty("-3668",
						"MDAS_AM_LIN_PARM_DELETION_ERROR MDAS_AM_LIN_PARM_DELETION_ERROR");
		srbException
				.setProperty("-3669",
						"MDAS_AM_LIN_RSRC_DELETION_ERROR MDAS_AM_LIN_RSRC_DELETION_ERROR");
		srbException
				.setProperty("-3670",
						"MDAS_AM_CNVT_MTHID_DELETION_ERROR MDAS_AM_CNVT_MTHID_DELETION_ERROR");
		srbException
				.setProperty("-3671",
						"MDAS_AM_CNVT_MTHTY_DELETION_ERROR MDAS_AM_CNVT_MTHTY_DELETION_ERROR");
		srbException
				.setProperty("-3672",
						"MDAS_AM_APPL_PARM_DELETION_ERROR MDAS_AM_APPL_PARM_DELETION_ERROR");
		srbException
				.setProperty("-3673",
						"MDAS_AM_APPL_OUT_DELETION_ERROR MDAS_AM_APPL_OUT_DELETION_ERROR");
		srbException
				.setProperty("-3674",
						"MDAS_AM_APPL_IN_DELETION_ERROR MDAS_AM_APPL_IN_DELETION_ERROR");
		srbException
				.setProperty("-3675",
						"MDAS_AM_APPL_RQR_DELETION_ERROR MDAS_AM_APPL_RQR_DELETION_ERROR");
		srbException
				.setProperty("-3676",
						"MDAS_AM_APPL_PRED_DELETION_ERROR MDAS_AM_APPL_PRED_DELETION_ERROR");
		srbException
				.setProperty("-3677",
						"MDAS_AM_COMP_MAP_DELETION_ERROR MDAS_AM_COMP_MAP_DELETION_ERROR");
		srbException
				.setProperty("-3678",
						"MDAS_AM_COMP_MAPDS_DELETION_ERROR MDAS_AM_COMP_MAPDS_DELETION_ERROR");
		srbException
				.setProperty("-3679",
						"MDAS_AM_COMP_MAPPR_DELETION_ERROR MDAS_AM_COMP_MAPPR_DELETION_ERROR");
		srbException.setProperty("-3680",
				"MDAS_AM_SD_DELETION_ERROR MDAS_AM_SD_DELETION_ERROR");
		srbException.setProperty("-3681",
				"MDAS_AM_TYP_SD_DELETION_ERROR MDAS_AM_TYP_SD_DELETION_ERROR");
		srbException.setProperty("-3682",
				"MDAS_AR_ALIAS_DELETION_ERROR MDAS_AR_ALIAS_DELETION_ERROR");
		srbException.setProperty("-3683",
				"MDAS_AR_DOMN_DELETION_ERROR MDAS_AR_DOMN_DELETION_ERROR");
		srbException
				.setProperty("-3684",
						"MDAS_AR_AUTH_KEY_DELETION_ERROR MDAS_AR_AUTH_KEY_DELETION_ERROR");
		srbException
				.setProperty("-3685",
						"MDAS_AR_DECR_KEY_DELETION_ERROR MDAS_AR_DECR_KEY_DELETION_ERROR");
		srbException.setProperty("-3686",
				"MDAS_AR_REPL_DELETION_ERROR MDAS_AR_REPL_DELETION_ERROR");
		srbException
				.setProperty("-3687",
						"MDAS_AR_COMMENTS_DELETION_ERROR MDAS_AR_COMMENTS_DELETION_ERROR");
		srbException.setProperty("-3688",
				"MDAS_AR_LOCK_DELETION_ERROR MDAS_AR_LOCK_DELETION_ERROR");
		srbException.setProperty("-3689",
				"MDAS_AR_ACCS_DELETION_ERROR MDAS_AR_ACCS_DELETION_ERROR");
		srbException.setProperty("-3690",
				"MDAS_AR_SUMM_DELETION_ERROR MDAS_AR_SUMM_DELETION_ERROR");
		srbException.setProperty("-3691",
				"MDAS_AR_AUDIT_DELETION_ERROR MDAS_AR_AUDIT_DELETION_ERROR");
		srbException
				.setProperty("-3692",
						"MDAS_AR_LIN_RSRC_DELETION_ERROR MDAS_AR_LIN_RSRC_DELETION_ERROR");
		srbException
				.setProperty("-3693",
						"MDAS_AR_LIN_METH_DELETION_ERROR MDAS_AR_LIN_METH_DELETION_ERROR");
		srbException
				.setProperty("-3694",
						"MDAS_AR_LIN_USER_DELETION_ERROR MDAS_AR_LIN_USER_DELETION_ERROR");
		srbException
				.setProperty("-3695",
						"MDAS_AR_LIN_PARM_DELETION_ERROR MDAS_AR_LIN_PARM_DELETION_ERROR");
		srbException
				.setProperty("-3696",
						"MDAS_AR_LIN_DATA_DELETION_ERROR MDAS_AR_LIN_DATA_DELETION_ERROR");
		srbException
				.setProperty("-3697",
						"MDAS_AR_FON_RSRC_DELETION_ERROR MDAS_AR_FON_RSRC_DELETION_ERROR");
		srbException
				.setProperty("-3698",
						"MDAS_AR_APPL_PRED_DELETION_ERROR MDAS_AR_APPL_PRED_DELETION_ERROR");
		srbException.setProperty("-3699",
				"MDAS_AR_SD_DELETION_ERROR MDAS_AR_SD_DELETION_ERROR");
		srbException.setProperty("-3700",
				"MDAS_AR_TYP_SD_DELETION_ERROR MDAS_AR_TYP_SD_DELETION_ERROR");
		srbException.setProperty("-3701",
				"MDAS_AU_INFO_DELETION_ERROR MDAS_AU_INFO_DELETION_ERROR");
		srbException.setProperty("-3702",
				"MDAS_AU_ALIAS_DELETION_ERROR MDAS_AU_ALIAS_DELETION_ERROR");
		srbException.setProperty("-3703",
				"MDAS_AU_GROUP_DELETION_ERROR MDAS_AU_GROUP_DELETION_ERROR");
		srbException.setProperty("-3704",
				"MDAS_AU_DOMN_DELETION_ERROR MDAS_AU_DOMN_DELETION_ERROR");
		srbException
				.setProperty("-3705",
						"MDAS_AU_AUTH_KEY_DELETION_ERROR MDAS_AU_AUTH_KEY_DELETION_ERROR");
		srbException
				.setProperty("-3706",
						"MDAS_AU_DECR_KEY_DELETION_ERROR MDAS_AU_DECR_KEY_DELETION_ERROR");
		srbException.setProperty("-3707",
				"MDAS_AU_SUMM_DELETION_ERROR MDAS_AU_SUMM_DELETION_ERROR");
		srbException.setProperty("-3708",
				"MDAS_AU_AUDIT_DELETION_ERROR MDAS_AU_AUDIT_DELETION_ERROR");
		srbException.setProperty("-3709",
				"MDAS_AU_SD_DELETION_ERROR MDAS_AU_SD_DELETION_ERROR");
		srbException.setProperty("-3710",
				"MDAS_AU_TYP_SD_DELETION_ERROR MDAS_AU_TYP_SD_DELETION_ERROR");
		srbException.setProperty("-3711",
				"MDAS_COUNTER_DELETION_ERROR MDAS_COUNTER_DELETION_ERROR");
		srbException
				.setProperty("-3712",
						"MDAS_SD_KEYWORD_DELETION_ERROR MDAS_SD_KEYWORD_DELETION_ERROR");
		srbException
				.setProperty("-3713",
						"MDAS_TD_DATA_ACCS_DELETION_ERROR MDAS_TD_DATA_ACCS_DELETION_ERROR");
		srbException.setProperty("-3714",
				"MDAS_AD_ACCESS_DELETION_ERROR MDAS_AD_ACCESS_DELETION_ERROR");
		srbException
				.setProperty("-3715",
						"MDAS_AD_GRP_ACCS_DELETION_ERROR MDAS_AD_GRP_ACCS_DELETION_ERROR");
		srbException
				.setProperty("-3716",
						"MDAS_AR_PHYSICAL_DELETION_ERROR MDAS_AR_PHYSICAL_DELETION_ERROR");
		srbException
				.setProperty("-3717",
						"MDAS_AU_TCKT_DATA_DELETION_ERROR MDAS_AU_TCKT_DATA_DELETION_ERROR");
		srbException
				.setProperty("-3718",
						"MDAS_AU_TCKT_GRP_DELETION_ERROR MDAS_AU_TCKT_GRP_DELETION_ERROR");
		srbException
				.setProperty("-3719",
						"MDAS_AR_DB_RSRC_DELETION_ERROR MDAS_AR_DB_RSRC_DELETION_ERROR");
		srbException
				.setProperty("-3720",
						"MDAS_TD_CONTAINER_DELETION_ERROR MDAS_TD_CONTAINER_DELETION_ERROR");
		srbException
				.setProperty("-3721",
						"MDAS_TD_RSRC_CLASS_DELETION_ERROR MDAS_TD_RSRC_CLASS_DELETION_ERROR");
		srbException
				.setProperty("-3722",
						"MDAS_AU_AUTH_MAP_DELETION_ERROR MDAS_AU_AUTH_MAP_DELETION_ERROR");
		srbException.setProperty("-3723",
				"MDAS_AR_AUTH_DELETION_ERROR MDAS_AR_AUTH_DELETION_ERROR");
		srbException
				.setProperty("-3724",
						"MDAS_TD_AUTH_SCHM_DELETION_ERROR MDAS_TD_AUTH_SCHM_DELETION_ERROR");
		srbException
				.setProperty("-3725",
						"MDAS_AD_ANNOTATION_DELETION_ERROR MDAS_AD_ANNOTATION_DELETION_ERROR");
		srbException.setProperty("-3726",
				"MDAS_AD_CLASS_DELETION_ERROR MDAS_AD_CLASS_DELETION_ERROR");
		srbException
				.setProperty("-3727",
						"MDAS_AD_DATA_INDEX_DELETION_ERROR MDAS_AD_DATA_INDEX_DELETION_ERROR");
		srbException
				.setProperty("-3728",
						"MDAS_AD_DTYP_INDEX_DELETION_ERROR MDAS_AD_DTYP_INDEX_DELETION_ERROR");
		srbException
				.setProperty("-3729",
						"MDAS_AD_COLL_INDEX_DELETION_ERROR MDAS_AD_COLL_INDEX_DELETION_ERROR");
		srbException
				.setProperty("-3730",
						"MDAS_AD_DATA_METH_DELETION_ERROR MDAS_AD_DATA_METH_DELETION_ERROR");
		srbException
				.setProperty("-3731",
						"MDAS_AD_DTYP_METH_DELETION_ERROR MDAS_AD_DTYP_METH_DELETION_ERROR");
		srbException
				.setProperty("-3732",
						"MDAS_AD_COLL_METH_DELETION_ERROR MDAS_AD_COLL_METH_DELETION_ERROR");
		srbException
				.setProperty("-3733",
						"MDAS_AD_STRCT_BLOB_DELETION_ERROR MDAS_AD_STRCT_BLOB_DELETION_ERROR");
		srbException.setProperty("-3734",
				"MDAS_AD_MDATA_DELETION_ERROR MDAS_AD_MDATA_DELETION_ERROR");
		srbException
				.setProperty("-3735",
						"MDAS_AD_COLLCONT_DELETION_ERROR MDAS_AD_COLLCONT_DELETION_ERROR");
		srbException
				.setProperty("-3736",
						"MDAS_AD_COLLMDATA_DELETION_ERROR MDAS_AD_COLLMDATA_DELETION_ERROR");
		srbException
				.setProperty("-3737",
						"MDAS_AC_ANNOTATION_DELETION_ERROR MDAS_AC_ANNOTATION_DELETION_ERROR");
		srbException
				.setProperty("-3738",
						"MDAS_AR_CMPND_RSRC_DELETION_ERROR MDAS_AR_CMPND_RSRC_DELETION_ERROR");
		srbException
				.setProperty("-3739",
						"MDAS_AD_COMPOUND_DELETION_ERROR MDAS_AD_COMPOUND_DELETION_ERROR");
		srbException
				.setProperty("-3740",
						"MDAS_AR_TAPE_INFO_DELETION_ERROR MDAS_AR_TAPE_INFO_DELETION_ERROR");
		srbException.setProperty("-3741",
				"MDAS_AD_MISC1_DELETION_ERROR MDAS_AD_MISC1_DELETION_ERROR");
		srbException.setProperty("-3742",
				"MDAS_AU_MDATA_DELETION_ERROR MDAS_AU_MDATA_DELETION_ERROR");
		srbException.setProperty("-3743",
				"MDAS_AR_MDATA_DELETION_ERROR MDAS_AR_MDATA_DELETION_ERROR");
		srbException.setProperty("-3744",
				"MDAS_TD_ZONE_DELETION_ERROR MDAS_TD_ZONE_DELETION_ERROR");
		srbException.setProperty("-3745",
				"MDAS_AD_GUID_DELETION_ERROR MDAS_AD_GUID_DELETION_ERROR");
		srbException.setProperty("-3746",
				"MDAS_AR_INFO_DELETION_ERROR MDAS_AR_INFO_DELETION_ERROR");
		srbException
				.setProperty("-3747",
						"MDAS_AR_DAILY_TOT_DELETION_ERROR MDAS_AR_DAILY_TOT_DELETION_ERROR");
		srbException
				.setProperty("-3748",
						"MDAS_AR_USE_QUOTA_DELETION_ERROR MDAS_AR_USE_QUOTA_DELETION_ERROR");
		srbException.setProperty("-3801",
				"MDAS_TD_LOCK_UPDATE_ERROR MDAS_TD_LOCK_UPDATE_ERROR");
		srbException.setProperty("-3802",
				"MDAS_TD_DOMN_UPDATE_ERROR MDAS_TD_DOMN_UPDATE_ERROR");
		srbException.setProperty("-3803",
				"MDAS_TD_DATA_GRP_UPDATE_ERROR MDAS_TD_DATA_GRP_UPDATE_ERROR");
		srbException.setProperty("-3804",
				"MDAS_TD_DATA_TYP_UPDATE_ERROR MDAS_TD_DATA_TYP_UPDATE_ERROR");
		srbException.setProperty("-3805",
				"MDAS_TD_METH_TYP_UPDATE_ERROR MDAS_TD_METH_TYP_UPDATE_ERROR");
		srbException.setProperty("-3806",
				"MDAS_TD_RSRC_TYP_UPDATE_ERROR MDAS_TD_RSRC_TYP_UPDATE_ERROR");
		srbException.setProperty("-3807",
				"MDAS_TD_USER_TYP_UPDATE_ERROR MDAS_TD_USER_TYP_UPDATE_ERROR");
		srbException.setProperty("-3808",
				"MDAS_TD_EXEC_TYP_UPDATE_ERROR MDAS_TD_EXEC_TYP_UPDATE_ERROR");
		srbException.setProperty("-3809",
				"MDAS_TD_LOCN_UPDATE_ERROR MDAS_TD_LOCN_UPDATE_ERROR");
		srbException
				.setProperty("-3810",
						"MDAS_TD_RSRC_FUNC_UPDATE_ERROR MDAS_TD_RSRC_FUNC_UPDATE_ERROR");
		srbException
				.setProperty("-3811",
						"MDAS_TD_DS_FLDTYP_UPDATE_ERROR MDAS_TD_DS_FLDTYP_UPDATE_ERROR");
		srbException.setProperty("-3812",
				"MDAS_TD_DS_SCHM_UPDATE_ERROR MDAS_TD_DS_SCHM_UPDATE_ERROR");
		srbException
				.setProperty("-3813",
						"MDAS_TD_DS_SCHMDSC_UPDATE_ERROR MDAS_TD_DS_SCHMDSC_UPDATE_ERROR");
		srbException
				.setProperty("-3814",
						"MDAS_TD_SPEC_SUMM_UPDATE_ERROR MDAS_TD_SPEC_SUMM_UPDATE_ERROR");
		srbException.setProperty("-3815",
				"MDAS_TD_ACTN_UPDATE_ERROR MDAS_TD_ACTN_UPDATE_ERROR");
		srbException.setProperty("-3816",
				"MDAS_TD_TCKT_UPDATE_ERROR MDAS_TD_TCKT_UPDATE_ERROR");
		srbException.setProperty("-3817",
				"MDAS_TD_AUTH_UPDATE_ERROR MDAS_TD_AUTH_UPDATE_ERROR");
		srbException.setProperty("-3818",
				"MDAS_TD_VERI_UPDATE_ERROR MDAS_TD_VERI_UPDATE_ERROR");
		srbException.setProperty("-3819",
				"MDAS_TD_ENCR_UPDATE_ERROR MDAS_TD_ENCR_UPDATE_ERROR");
		srbException.setProperty("-3820",
				"MDAS_TD_DECR_UPDATE_ERROR MDAS_TD_DECR_UPDATE_ERROR");
		srbException
				.setProperty("-3821",
						"MDAS_TD_RSRC_ACCS_UPDATE_ERROR MDAS_TD_RSRC_ACCS_UPDATE_ERROR");
		srbException
				.setProperty("-3822",
						"MDAS_TD_METH_ACCS_UPDATE_ERROR MDAS_TD_METH_ACCS_UPDATE_ERROR");
		srbException.setProperty("-3823",
				"MDAS_TD_DS_ACCS_UPDATE_ERROR MDAS_TD_DS_ACCS_UPDATE_ERROR");
		srbException.setProperty("-3824",
				"MDAS_CD_DATA_UPDATE_ERROR MDAS_CD_DATA_UPDATE_ERROR");
		srbException.setProperty("-3825",
				"MDAS_CD_METH_UPDATE_ERROR MDAS_CD_METH_UPDATE_ERROR");
		srbException.setProperty("-3826",
				"MDAS_CD_RSRC_UPDATE_ERROR MDAS_CD_RSRC_UPDATE_ERROR");
		srbException.setProperty("-3827",
				"MDAS_CD_USER_UPDATE_ERROR MDAS_CD_USER_UPDATE_ERROR");
		srbException.setProperty("-3828",
				"MDAS_TD_REPL_TYP_UPDATE_ERROR MDAS_TD_REPL_TYP_UPDATE_ERROR");
		srbException
				.setProperty("-3829",
						"MDAS_TD_DS_REPPLCY_UPDATE_ERROR MDAS_TD_DS_REPPLCY_UPDATE_ERROR");
		srbException
				.setProperty("-3830",
						"MDAS_TD_DS_PRTPLCY_UPDATE_ERROR MDAS_TD_DS_PRTPLCY_UPDATE_ERROR");
		srbException.setProperty("-3831",
				"MDAS_TD_DS_TRIG_UPDATE_ERROR MDAS_TD_DS_TRIG_UPDATE_ERROR");
		srbException.setProperty("-3832",
				"MDAS_TD_DS_AGGR_UPDATE_ERROR MDAS_TD_DS_AGGR_UPDATE_ERROR");
		srbException
				.setProperty("-3833",
						"MDAS_TD_MTHREP_PLC_UPDATE_ERROR MDAS_TD_MTHREP_PLC_UPDATE_ERROR");
		srbException
				.setProperty("-3834",
						"MDAS_TD_RSRREP_PLC_UPDATE_ERROR MDAS_TD_RSRREP_PLC_UPDATE_ERROR");
		srbException.setProperty("-3835",
				"MDAS_AD_ALIAS_UPDATE_ERROR MDAS_AD_ALIAS_UPDATE_ERROR");
		srbException.setProperty("-3836",
				"MDAS_AD_DOMN_UPDATE_ERROR MDAS_AD_DOMN_UPDATE_ERROR");
		srbException.setProperty("-3837",
				"MDAS_AD_AUTH_KEY_UPDATE_ERROR MDAS_AD_AUTH_KEY_UPDATE_ERROR");
		srbException.setProperty("-3838",
				"MDAS_AD_REPL_UPDATE_ERROR MDAS_AD_REPL_UPDATE_ERROR");
		srbException.setProperty("-3839",
				"MDAS_AD_COMMENTS_UPDATE_ERROR MDAS_AD_COMMENTS_UPDATE_ERROR");
		srbException.setProperty("-3840",
				"MDAS_AD_LOCK_UPDATE_ERROR MDAS_AD_LOCK_UPDATE_ERROR");
		srbException.setProperty("-3841",
				"MDAS_AD_ACCS_UPDATE_ERROR MDAS_AD_ACCS_UPDATE_ERROR");
		srbException.setProperty("-3842",
				"MDAS_AD_PART_UPDATE_ERROR MDAS_AD_PART_UPDATE_ERROR");
		srbException.setProperty("-3843",
				"MDAS_AD_AGGR_UPDATE_ERROR MDAS_AD_AGGR_UPDATE_ERROR");
		srbException.setProperty("-3844",
				"MDAS_AD_SUMM_UPDATE_ERROR MDAS_AD_SUMM_UPDATE_ERROR");
		srbException.setProperty("-3845",
				"MDAS_AD_TRIG_UPDATE_ERROR MDAS_AD_TRIG_UPDATE_ERROR");
		srbException.setProperty("-3846",
				"MDAS_AD_AUDIT_UPDATE_ERROR MDAS_AD_AUDIT_UPDATE_ERROR");
		srbException.setProperty("-3847",
				"MDAS_AD_LIN_DATA_UPDATE_ERROR MDAS_AD_LIN_DATA_UPDATE_ERROR");
		srbException.setProperty("-3848",
				"MDAS_AD_LIN_METH_UPDATE_ERROR MDAS_AD_LIN_METH_UPDATE_ERROR");
		srbException.setProperty("-3849",
				"MDAS_AD_LIN_USER_UPDATE_ERROR MDAS_AD_LIN_USER_UPDATE_ERROR");
		srbException.setProperty("-3850",
				"MDAS_AD_LIN_PARM_UPDATE_ERROR MDAS_AD_LIN_PARM_UPDATE_ERROR");
		srbException.setProperty("-3851",
				"MDAS_AD_LIN_RSRC_UPDATE_ERROR MDAS_AD_LIN_RSRC_UPDATE_ERROR");
		srbException.setProperty("-3852",
				"MDAS_AD_FON_DATA_UPDATE_ERROR MDAS_AD_FON_DATA_UPDATE_ERROR");
		srbException.setProperty("-3853",
				"MDAS_AD_SD_UPDATE_ERROR MDAS_AD_SD_UPDATE_ERROR");
		srbException.setProperty("-3854",
				"MDAS_AD_TYP_SD_UPDATE_ERROR MDAS_AD_TYP_SD_UPDATE_ERROR");
		srbException.setProperty("-3855",
				"MDAS_AM_ALIAS_UPDATE_ERROR MDAS_AM_ALIAS_UPDATE_ERROR");
		srbException.setProperty("-3856",
				"MDAS_AM_DOMN_UPDATE_ERROR MDAS_AM_DOMN_UPDATE_ERROR");
		srbException.setProperty("-3857",
				"MDAS_AM_AUTH_KEY_UPDATE_ERROR MDAS_AM_AUTH_KEY_UPDATE_ERROR");
		srbException.setProperty("-3858",
				"MDAS_AM_DECR_KEY_UPDATE_ERROR MDAS_AM_DECR_KEY_UPDATE_ERROR");
		srbException.setProperty("-3859",
				"MDAS_AM_REPL_UPDATE_ERROR MDAS_AM_REPL_UPDATE_ERROR");
		srbException.setProperty("-3860",
				"MDAS_AM_COMMENTS_UPDATE_ERROR MDAS_AM_COMMENTS_UPDATE_ERROR");
		srbException.setProperty("-3861",
				"MDAS_AM_LOCK_UPDATE_ERROR MDAS_AM_LOCK_UPDATE_ERROR");
		srbException.setProperty("-3862",
				"MDAS_AM_ACCS_UPDATE_ERROR MDAS_AM_ACCS_UPDATE_ERROR");
		srbException.setProperty("-3863",
				"MDAS_AM_SUMM_UPDATE_ERROR MDAS_AM_SUMM_UPDATE_ERROR");
		srbException.setProperty("-3864",
				"MDAS_AM_AUDIT_UPDATE_ERROR MDAS_AM_AUDIT_UPDATE_ERROR");
		srbException.setProperty("-3865",
				"MDAS_AM_LIN_METH_UPDATE_ERROR MDAS_AM_LIN_METH_UPDATE_ERROR");
		srbException.setProperty("-3866",
				"MDAS_AM_LIN_DATA_UPDATE_ERROR MDAS_AM_LIN_DATA_UPDATE_ERROR");
		srbException.setProperty("-3867",
				"MDAS_AM_LIN_USER_UPDATE_ERROR MDAS_AM_LIN_USER_UPDATE_ERROR");
		srbException.setProperty("-3868",
				"MDAS_AM_LIN_PARM_UPDATE_ERROR MDAS_AM_LIN_PARM_UPDATE_ERROR");
		srbException.setProperty("-3869",
				"MDAS_AM_LIN_RSRC_UPDATE_ERROR MDAS_AM_LIN_RSRC_UPDATE_ERROR");
		srbException
				.setProperty("-3870",
						"MDAS_AM_CNVT_MTHID_UPDATE_ERROR MDAS_AM_CNVT_MTHID_UPDATE_ERROR");
		srbException
				.setProperty("-3871",
						"MDAS_AM_CNVT_MTHTY_UPDATE_ERROR MDAS_AM_CNVT_MTHTY_UPDATE_ERROR");
		srbException
				.setProperty("-3872",
						"MDAS_AM_APPL_PARM_UPDATE_ERROR MDAS_AM_APPL_PARM_UPDATE_ERROR");
		srbException.setProperty("-3873",
				"MDAS_AM_APPL_OUT_UPDATE_ERROR MDAS_AM_APPL_OUT_UPDATE_ERROR");
		srbException.setProperty("-3874",
				"MDAS_AM_APPL_IN_UPDATE_ERROR MDAS_AM_APPL_IN_UPDATE_ERROR");
		srbException.setProperty("-3875",
				"MDAS_AM_APPL_RQR_UPDATE_ERROR MDAS_AM_APPL_RQR_UPDATE_ERROR");
		srbException
				.setProperty("-3876",
						"MDAS_AM_APPL_PRED_UPDATE_ERROR MDAS_AM_APPL_PRED_UPDATE_ERROR");
		srbException.setProperty("-3877",
				"MDAS_AM_COMP_MAP_UPDATE_ERROR MDAS_AM_COMP_MAP_UPDATE_ERROR");
		srbException
				.setProperty("-3878",
						"MDAS_AM_COMP_MAPDS_UPDATE_ERROR MDAS_AM_COMP_MAPDS_UPDATE_ERROR");
		srbException
				.setProperty("-3879",
						"MDAS_AM_COMP_MAPPR_UPDATE_ERROR MDAS_AM_COMP_MAPPR_UPDATE_ERROR");
		srbException.setProperty("-3880",
				"MDAS_AM_SD_UPDATE_ERROR MDAS_AM_SD_UPDATE_ERROR");
		srbException.setProperty("-3881",
				"MDAS_AM_TYP_SD_UPDATE_ERROR MDAS_AM_TYP_SD_UPDATE_ERROR");
		srbException.setProperty("-3882",
				"MDAS_AR_ALIAS_UPDATE_ERROR MDAS_AR_ALIAS_UPDATE_ERROR");
		srbException.setProperty("-3883",
				"MDAS_AR_DOMN_UPDATE_ERROR MDAS_AR_DOMN_UPDATE_ERROR");
		srbException.setProperty("-3884",
				"MDAS_AR_AUTH_KEY_UPDATE_ERROR MDAS_AR_AUTH_KEY_UPDATE_ERROR");
		srbException.setProperty("-3885",
				"MDAS_AR_DECR_KEY_UPDATE_ERROR MDAS_AR_DECR_KEY_UPDATE_ERROR");
		srbException.setProperty("-3886",
				"MDAS_AR_REPL_UPDATE_ERROR MDAS_AR_REPL_UPDATE_ERROR");
		srbException.setProperty("-3887",
				"MDAS_AR_COMMENTS_UPDATE_ERROR MDAS_AR_COMMENTS_UPDATE_ERROR");
		srbException.setProperty("-3888",
				"MDAS_AR_LOCK_UPDATE_ERROR MDAS_AR_LOCK_UPDATE_ERROR");
		srbException.setProperty("-3889",
				"MDAS_AR_ACCS_UPDATE_ERROR MDAS_AR_ACCS_UPDATE_ERROR");
		srbException.setProperty("-3890",
				"MDAS_AR_SUMM_UPDATE_ERROR MDAS_AR_SUMM_UPDATE_ERROR");
		srbException.setProperty("-3891",
				"MDAS_AR_AUDIT_UPDATE_ERROR MDAS_AR_AUDIT_UPDATE_ERROR");
		srbException.setProperty("-3892",
				"MDAS_AR_LIN_RSRC_UPDATE_ERROR MDAS_AR_LIN_RSRC_UPDATE_ERROR");
		srbException.setProperty("-3893",
				"MDAS_AR_LIN_METH_UPDATE_ERROR MDAS_AR_LIN_METH_UPDATE_ERROR");
		srbException.setProperty("-3894",
				"MDAS_AR_LIN_USER_UPDATE_ERROR MDAS_AR_LIN_USER_UPDATE_ERROR");
		srbException.setProperty("-3895",
				"MDAS_AR_LIN_PARM_UPDATE_ERROR MDAS_AR_LIN_PARM_UPDATE_ERROR");
		srbException.setProperty("-3896",
				"MDAS_AR_LIN_DATA_UPDATE_ERROR MDAS_AR_LIN_DATA_UPDATE_ERROR");
		srbException.setProperty("-3897",
				"MDAS_AR_FON_RSRC_UPDATE_ERROR MDAS_AR_FON_RSRC_UPDATE_ERROR");
		srbException
				.setProperty("-3898",
						"MDAS_AR_APPL_PRED_UPDATE_ERROR MDAS_AR_APPL_PRED_UPDATE_ERROR");
		srbException.setProperty("-3899",
				"MDAS_AR_SD_UPDATE_ERROR MDAS_AR_SD_UPDATE_ERROR");
		srbException.setProperty("-3900",
				"MDAS_AR_TYP_SD_UPDATE_ERROR MDAS_AR_TYP_SD_UPDATE_ERROR");
		srbException.setProperty("-3901",
				"MDAS_AU_INFO_UPDATE_ERROR MDAS_AU_INFO_UPDATE_ERROR");
		srbException.setProperty("-3902",
				"MDAS_AU_ALIAS_UPDATE_ERROR MDAS_AU_ALIAS_UPDATE_ERROR");
		srbException.setProperty("-3903",
				"MDAS_AU_GROUP_UPDATE_ERROR MDAS_AU_GROUP_UPDATE_ERROR");
		srbException.setProperty("-3904",
				"MDAS_AU_DOMN_UPDATE_ERROR MDAS_AU_DOMN_UPDATE_ERROR");
		srbException.setProperty("-3905",
				"MDAS_AU_AUTH_KEY_UPDATE_ERROR MDAS_AU_AUTH_KEY_UPDATE_ERROR");
		srbException.setProperty("-3906",
				"MDAS_AU_DECR_KEY_UPDATE_ERROR MDAS_AU_DECR_KEY_UPDATE_ERROR");
		srbException.setProperty("-3907",
				"MDAS_AU_SUMM_UPDATE_ERROR MDAS_AU_SUMM_UPDATE_ERROR");
		srbException.setProperty("-3908",
				"MDAS_AU_AUDIT_UPDATE_ERROR MDAS_AU_AUDIT_UPDATE_ERROR");
		srbException.setProperty("-3909",
				"MDAS_AU_SD_UPDATE_ERROR MDAS_AU_SD_UPDATE_ERROR");
		srbException.setProperty("-3910",
				"MDAS_AU_TYP_SD_UPDATE_ERROR MDAS_AU_TYP_SD_UPDATE_ERROR");
		srbException.setProperty("-3911",
				"MDAS_COUNTER_UPDATE_ERROR MDAS_COUNTER_UPDATE_ERROR");
		srbException.setProperty("-3912",
				"MDAS_SD_KEYWORD_UPDATE_ERROR MDAS_SD_KEYWORD_UPDATE_ERROR");
		srbException
				.setProperty("-3913",
						"MDAS_TD_DATA_ACCS_UPDATE_ERROR MDAS_TD_DATA_ACCS_UPDATE_ERROR");
		srbException.setProperty("-3914",
				"MDAS_AD_ACCESS_UPDATE_ERROR MDAS_AD_ACCESS_UPDATE_ERROR");
		srbException.setProperty("-3915",
				"MDAS_AD_GRP_ACCS_UPDATE_ERROR MDAS_AD_GRP_ACCS_UPDATE_ERROR");
		srbException.setProperty("-3916",
				"MDAS_AR_PHYSICAL_UPDATE_ERROR MDAS_AR_PHYSICAL_UPDATE_ERROR");
		srbException
				.setProperty("-3917",
						"MDAS_AU_TCKT_DATA_UPDATE_ERROR MDAS_AU_TCKT_DATA_UPDATE_ERROR");
		srbException.setProperty("-3918",
				"MDAS_AU_TCKT_GRP_UPDATE_ERROR MDAS_AU_TCKT_GRP_UPDATE_ERROR");
		srbException.setProperty("-3919",
				"MDAS_AR_DB_RSRC_UPDATE_ERROR MDAS_AR_DB_RSRC_UPDATE_ERROR");
		srbException
				.setProperty("-3920",
						"MDAS_TD_CONTAINER_UPDATE_ERROR MDAS_TD_CONTAINER_UPDATE_ERROR");
		srbException
				.setProperty("-3921",
						"MDAS_TD_RSRC_CLASS_UPDATE_ERROR MDAS_TD_RSRC_CLASS_UPDATE_ERROR");
		srbException.setProperty("-3922",
				"MDAS_AU_AUTH_MAP_UPDATE_ERROR MDAS_AU_AUTH_MAP_UPDATE_ERROR");
		srbException.setProperty("-3923",
				"MDAS_AR_AUTH_UPDATE_ERROR MDAS_AR_AUTH_UPDATE_ERROR");
		srbException
				.setProperty("-3924",
						"MDAS_TD_AUTH_SCHM_UPDATE_ERROR MDAS_TD_AUTH_SCHM_UPDATE_ERROR");
		srbException
				.setProperty("-3925",
						"MDAS_AD_ANNOTATION_UPDATE_ERROR MDAS_AD_ANNOTATION_UPDATE_ERROR");
		srbException.setProperty("-3926",
				"MDAS_AD_CLASS_UPDATE_ERROR MDAS_AD_CLASS_UPDATE_ERROR");
		srbException
				.setProperty("-3927",
						"MDAS_AD_DATA_INDEX_UPDATE_ERROR MDAS_AD_DATA_INDEX_UPDATE_ERROR");
		srbException
				.setProperty("-3928",
						"MDAS_AD_DTYP_INDEX_UPDATE_ERROR MDAS_AD_DTYP_INDEX_UPDATE_ERROR");
		srbException
				.setProperty("-3929",
						"MDAS_AD_COLL_INDEX_UPDATE_ERROR MDAS_AD_COLL_INDEX_UPDATE_ERROR");
		srbException
				.setProperty("-3930",
						"MDAS_AD_DATA_METH_UPDATE_ERROR MDAS_AD_DATA_METH_UPDATE_ERROR");
		srbException
				.setProperty("-3931",
						"MDAS_AD_DTYP_METH_UPDATE_ERROR MDAS_AD_DTYP_METH_UPDATE_ERROR");
		srbException
				.setProperty("-3932",
						"MDAS_AD_COLL_METH_UPDATE_ERROR MDAS_AD_COLL_METH_UPDATE_ERROR");
		srbException
				.setProperty("-3933",
						"MDAS_AD_STRCT_BLOB_UPDATE_ERROR MDAS_AD_STRCT_BLOB_UPDATE_ERROR");
		srbException.setProperty("-3934",
				"MDAS_AD_MDATA_UPDATE_ERROR MDAS_AD_MDATA_UPDATE_ERROR");
		srbException.setProperty("-3935",
				"MDAS_AD_COLLCONT_UPDATE_ERROR MDAS_AD_COLLCONT_UPDATE_ERROR");
		srbException
				.setProperty("-3936",
						"MDAS_AD_COLLMDATA_UPDATE_ERROR MDAS_AD_COLLMDATA_UPDATE_ERROR");
		srbException
				.setProperty("-3937",
						"MDAS_AC_ANNOTATION_UPDATE_ERROR MDAS_AC_ANNOTATION_UPDATE_ERROR");
		srbException
				.setProperty("-3938",
						"MDAS_AR_CMPND_RSRC_UPDATE_ERROR MDAS_AR_CMPND_RSRC_UPDATE_ERROR");
		srbException.setProperty("-3939",
				"MDAS_AD_COMPOUND_UPDATE_ERROR MDAS_AD_COMPOUND_UPDATE_ERROR");
		srbException
				.setProperty("-3940",
						"MDAS_AR_TAPE_INFO_UPDATE_ERROR MDAS_AR_TAPE_INFO_UPDATE_ERROR");
		srbException.setProperty("-3941",
				"MDAS_AD_MISC1_UPDATE_ERROR MDAS_AD_MISC1_UPDATE_ERROR");
		srbException.setProperty("-3942",
				"MDAS_AU_MDATA_UPDATE_ERROR MDAS_AU_MDATA_UPDATE_ERROR");
		srbException.setProperty("-3943",
				"MDAS_AR_MDATA_UPDATE_ERROR MDAS_AR_MDATA_UPDATE_ERROR");
		srbException.setProperty("-3944",
				"MDAS_TD_ZONE_UPDATE_ERROR MDAS_TD_ZONE_UPDATE_ERROR");
		srbException.setProperty("-3945",
				"MDAS_AD_GUID_UPDATE_ERROR MDAS_AD_GUID_UPDATE_ERROR");
		srbException.setProperty("-3946",
				"MDAS_AR_INFO_UPDATE_ERROR MDAS_AR_INFO_UPDATE_ERROR");
		srbException
				.setProperty("-3947",
						"MDAS_AR_DAILY_TOT_UPDATE_ERROR MDAS_AR_DAILY_TOT_UPDATE_ERROR");
		srbException
				.setProperty("-3948",
						"MDAS_AR_USE_QUOTA_UPDATE_ERROR MDAS_AR_USE_QUOTA_UPDATE_ERROR");
		srbException.setProperty("-4001",
				"MDAS_TD_LOCK_ACCESS_ERROR MDAS_TD_LOCK_ACCESS_ERROR");
		srbException.setProperty("-4002",
				"MDAS_TD_DOMN_ACCESS_ERROR MDAS_TD_DOMN_ACCESS_ERROR");
		srbException.setProperty("-4003",
				"MDAS_TD_DATA_GRP_ACCESS_ERROR MDAS_TD_DATA_GRP_ACCESS_ERROR");
		srbException.setProperty("-4004",
				"MDAS_TD_DATA_TYP_ACCESS_ERROR MDAS_TD_DATA_TYP_ACCESS_ERROR");
		srbException.setProperty("-4005",
				"MDAS_TD_METH_TYP_ACCESS_ERROR MDAS_TD_METH_TYP_ACCESS_ERROR");
		srbException.setProperty("-4006",
				"MDAS_TD_RSRC_TYP_ACCESS_ERROR MDAS_TD_RSRC_TYP_ACCESS_ERROR");
		srbException.setProperty("-4007",
				"MDAS_TD_USER_TYP_ACCESS_ERROR MDAS_TD_USER_TYP_ACCESS_ERROR");
		srbException.setProperty("-4008",
				"MDAS_TD_EXEC_TYP_ACCESS_ERROR MDAS_TD_EXEC_TYP_ACCESS_ERROR");
		srbException.setProperty("-4009",
				"MDAS_TD_LOCN_ACCESS_ERROR MDAS_TD_LOCN_ACCESS_ERROR");
		srbException
				.setProperty("-4010",
						"MDAS_TD_RSRC_FUNC_ACCESS_ERROR MDAS_TD_RSRC_FUNC_ACCESS_ERROR");
		srbException
				.setProperty("-4011",
						"MDAS_TD_DS_FLDTYP_ACCESS_ERROR MDAS_TD_DS_FLDTYP_ACCESS_ERROR");
		srbException.setProperty("-4012",
				"MDAS_TD_DS_SCHM_ACCESS_ERROR MDAS_TD_DS_SCHM_ACCESS_ERROR");
		srbException
				.setProperty("-4013",
						"MDAS_TD_DS_SCHMDSC_ACCESS_ERROR MDAS_TD_DS_SCHMDSC_ACCESS_ERROR");
		srbException
				.setProperty("-4014",
						"MDAS_TD_SPEC_SUMM_ACCESS_ERROR MDAS_TD_SPEC_SUMM_ACCESS_ERROR");
		srbException.setProperty("-4015",
				"MDAS_TD_ACTN_ACCESS_ERROR MDAS_TD_ACTN_ACCESS_ERROR");
		srbException.setProperty("-4016",
				"MDAS_TD_TCKT_ACCESS_ERROR MDAS_TD_TCKT_ACCESS_ERROR");
		srbException.setProperty("-4017",
				"MDAS_TD_AUTH_ACCESS_ERROR MDAS_TD_AUTH_ACCESS_ERROR");
		srbException.setProperty("-4018",
				"MDAS_TD_VERI_ACCESS_ERROR MDAS_TD_VERI_ACCESS_ERROR");
		srbException.setProperty("-4019",
				"MDAS_TD_ENCR_ACCESS_ERROR MDAS_TD_ENCR_ACCESS_ERROR");
		srbException.setProperty("-4020",
				"MDAS_TD_DECR_ACCESS_ERROR MDAS_TD_DECR_ACCESS_ERROR");
		srbException
				.setProperty("-4021",
						"MDAS_TD_RSRC_ACCS_ACCESS_ERROR MDAS_TD_RSRC_ACCS_ACCESS_ERROR");
		srbException
				.setProperty("-4022",
						"MDAS_TD_METH_ACCS_ACCESS_ERROR MDAS_TD_METH_ACCS_ACCESS_ERROR");
		srbException.setProperty("-4023",
				"MDAS_TD_DS_ACCS_ACCESS_ERROR MDAS_TD_DS_ACCS_ACCESS_ERROR");
		srbException.setProperty("-4024",
				"MDAS_CD_DATA_ACCESS_ERROR MDAS_CD_DATA_ACCESS_ERROR");
		srbException.setProperty("-4025",
				"MDAS_CD_METH_ACCESS_ERROR MDAS_CD_METH_ACCESS_ERROR");
		srbException.setProperty("-4026",
				"MDAS_CD_RSRC_ACCESS_ERROR MDAS_CD_RSRC_ACCESS_ERROR");
		srbException.setProperty("-4027",
				"MDAS_CD_USER_ACCESS_ERROR MDAS_CD_USER_ACCESS_ERROR");
		srbException.setProperty("-4028",
				"MDAS_TD_REPL_TYP_ACCESS_ERROR MDAS_TD_REPL_TYP_ACCESS_ERROR");
		srbException
				.setProperty("-4029",
						"MDAS_TD_DS_REPPLCY_ACCESS_ERROR MDAS_TD_DS_REPPLCY_ACCESS_ERROR");
		srbException
				.setProperty("-4030",
						"MDAS_TD_DS_PRTPLCY_ACCESS_ERROR MDAS_TD_DS_PRTPLCY_ACCESS_ERROR");
		srbException.setProperty("-4031",
				"MDAS_TD_DS_TRIG_ACCESS_ERROR MDAS_TD_DS_TRIG_ACCESS_ERROR");
		srbException.setProperty("-4032",
				"MDAS_TD_DS_AGGR_ACCESS_ERROR MDAS_TD_DS_AGGR_ACCESS_ERROR");
		srbException
				.setProperty("-4033",
						"MDAS_TD_MTHREP_PLC_ACCESS_ERROR MDAS_TD_MTHREP_PLC_ACCESS_ERROR");
		srbException
				.setProperty("-4034",
						"MDAS_TD_RSRREP_PLC_ACCESS_ERROR MDAS_TD_RSRREP_PLC_ACCESS_ERROR");
		srbException.setProperty("-4035",
				"MDAS_AD_ALIAS_ACCESS_ERROR MDAS_AD_ALIAS_ACCESS_ERROR");
		srbException.setProperty("-4036",
				"MDAS_AD_DOMN_ACCESS_ERROR MDAS_AD_DOMN_ACCESS_ERROR");
		srbException.setProperty("-4037",
				"MDAS_AD_AUTH_KEY_ACCESS_ERROR MDAS_AD_AUTH_KEY_ACCESS_ERROR");
		srbException.setProperty("-4038",
				"MDAS_AD_REPL_ACCESS_ERROR MDAS_AD_REPL_ACCESS_ERROR");
		srbException.setProperty("-4039",
				"MDAS_AD_COMMENTS_ACCESS_ERROR MDAS_AD_COMMENTS_ACCESS_ERROR");
		srbException.setProperty("-4040",
				"MDAS_AD_LOCK_ACCESS_ERROR MDAS_AD_LOCK_ACCESS_ERROR");
		srbException.setProperty("-4041",
				"MDAS_AD_ACCS_ACCESS_ERROR MDAS_AD_ACCS_ACCESS_ERROR");
		srbException.setProperty("-4042",
				"MDAS_AD_PART_ACCESS_ERROR MDAS_AD_PART_ACCESS_ERROR");
		srbException.setProperty("-4043",
				"MDAS_AD_AGGR_ACCESS_ERROR MDAS_AD_AGGR_ACCESS_ERROR");
		srbException.setProperty("-4044",
				"MDAS_AD_SUMM_ACCESS_ERROR MDAS_AD_SUMM_ACCESS_ERROR");
		srbException.setProperty("-4045",
				"MDAS_AD_TRIG_ACCESS_ERROR MDAS_AD_TRIG_ACCESS_ERROR");
		srbException.setProperty("-4046",
				"MDAS_AD_AUDIT_ACCESS_ERROR MDAS_AD_AUDIT_ACCESS_ERROR");
		srbException.setProperty("-4047",
				"MDAS_AD_LIN_DATA_ACCESS_ERROR MDAS_AD_LIN_DATA_ACCESS_ERROR");
		srbException.setProperty("-4048",
				"MDAS_AD_LIN_METH_ACCESS_ERROR MDAS_AD_LIN_METH_ACCESS_ERROR");
		srbException.setProperty("-4049",
				"MDAS_AD_LIN_USER_ACCESS_ERROR MDAS_AD_LIN_USER_ACCESS_ERROR");
		srbException.setProperty("-4050",
				"MDAS_AD_LIN_PARM_ACCESS_ERROR MDAS_AD_LIN_PARM_ACCESS_ERROR");
		srbException.setProperty("-4051",
				"MDAS_AD_LIN_RSRC_ACCESS_ERROR MDAS_AD_LIN_RSRC_ACCESS_ERROR");
		srbException.setProperty("-4052",
				"MDAS_AD_FON_DATA_ACCESS_ERROR MDAS_AD_FON_DATA_ACCESS_ERROR");
		srbException.setProperty("-4053",
				"MDAS_AD_SD_ACCESS_ERROR MDAS_AD_SD_ACCESS_ERROR");
		srbException.setProperty("-4054",
				"MDAS_AD_TYP_SD_ACCESS_ERROR MDAS_AD_TYP_SD_ACCESS_ERROR");
		srbException.setProperty("-4055",
				"MDAS_AM_ALIAS_ACCESS_ERROR MDAS_AM_ALIAS_ACCESS_ERROR");
		srbException.setProperty("-4056",
				"MDAS_AM_DOMN_ACCESS_ERROR MDAS_AM_DOMN_ACCESS_ERROR");
		srbException.setProperty("-4057",
				"MDAS_AM_AUTH_KEY_ACCESS_ERROR MDAS_AM_AUTH_KEY_ACCESS_ERROR");
		srbException.setProperty("-4058",
				"MDAS_AM_DECR_KEY_ACCESS_ERROR MDAS_AM_DECR_KEY_ACCESS_ERROR");
		srbException.setProperty("-4059",
				"MDAS_AM_REPL_ACCESS_ERROR MDAS_AM_REPL_ACCESS_ERROR");
		srbException.setProperty("-4060",
				"MDAS_AM_COMMENTS_ACCESS_ERROR MDAS_AM_COMMENTS_ACCESS_ERROR");
		srbException.setProperty("-4061",
				"MDAS_AM_LOCK_ACCESS_ERROR MDAS_AM_LOCK_ACCESS_ERROR");
		srbException.setProperty("-4062",
				"MDAS_AM_ACCS_ACCESS_ERROR MDAS_AM_ACCS_ACCESS_ERROR");
		srbException.setProperty("-4063",
				"MDAS_AM_SUMM_ACCESS_ERROR MDAS_AM_SUMM_ACCESS_ERROR");
		srbException.setProperty("-4064",
				"MDAS_AM_AUDIT_ACCESS_ERROR MDAS_AM_AUDIT_ACCESS_ERROR");
		srbException.setProperty("-4065",
				"MDAS_AM_LIN_METH_ACCESS_ERROR MDAS_AM_LIN_METH_ACCESS_ERROR");
		srbException.setProperty("-4066",
				"MDAS_AM_LIN_DATA_ACCESS_ERROR MDAS_AM_LIN_DATA_ACCESS_ERROR");
		srbException.setProperty("-4067",
				"MDAS_AM_LIN_USER_ACCESS_ERROR MDAS_AM_LIN_USER_ACCESS_ERROR");
		srbException.setProperty("-4068",
				"MDAS_AM_LIN_PARM_ACCESS_ERROR MDAS_AM_LIN_PARM_ACCESS_ERROR");
		srbException.setProperty("-4069",
				"MDAS_AM_LIN_RSRC_ACCESS_ERROR MDAS_AM_LIN_RSRC_ACCESS_ERROR");
		srbException
				.setProperty("-4070",
						"MDAS_AM_CNVT_MTHID_ACCESS_ERROR MDAS_AM_CNVT_MTHID_ACCESS_ERROR");
		srbException
				.setProperty("-4071",
						"MDAS_AM_CNVT_MTHTY_ACCESS_ERROR MDAS_AM_CNVT_MTHTY_ACCESS_ERROR");
		srbException
				.setProperty("-4072",
						"MDAS_AM_APPL_PARM_ACCESS_ERROR MDAS_AM_APPL_PARM_ACCESS_ERROR");
		srbException.setProperty("-4073",
				"MDAS_AM_APPL_OUT_ACCESS_ERROR MDAS_AM_APPL_OUT_ACCESS_ERROR");
		srbException.setProperty("-4074",
				"MDAS_AM_APPL_IN_ACCESS_ERROR MDAS_AM_APPL_IN_ACCESS_ERROR");
		srbException.setProperty("-4075",
				"MDAS_AM_APPL_RQR_ACCESS_ERROR MDAS_AM_APPL_RQR_ACCESS_ERROR");
		srbException
				.setProperty("-4076",
						"MDAS_AM_APPL_PRED_ACCESS_ERROR MDAS_AM_APPL_PRED_ACCESS_ERROR");
		srbException.setProperty("-4077",
				"MDAS_AM_COMP_MAP_ACCESS_ERROR MDAS_AM_COMP_MAP_ACCESS_ERROR");
		srbException
				.setProperty("-4078",
						"MDAS_AM_COMP_MAPDS_ACCESS_ERROR MDAS_AM_COMP_MAPDS_ACCESS_ERROR");
		srbException
				.setProperty("-4079",
						"MDAS_AM_COMP_MAPPR_ACCESS_ERROR MDAS_AM_COMP_MAPPR_ACCESS_ERROR");
		srbException.setProperty("-4080",
				"MDAS_AM_SD_ACCESS_ERROR MDAS_AM_SD_ACCESS_ERROR");
		srbException.setProperty("-4081",
				"MDAS_AM_TYP_SD_ACCESS_ERROR MDAS_AM_TYP_SD_ACCESS_ERROR");
		srbException.setProperty("-4082",
				"MDAS_AR_ALIAS_ACCESS_ERROR MDAS_AR_ALIAS_ACCESS_ERROR");
		srbException.setProperty("-4083",
				"MDAS_AR_DOMN_ACCESS_ERROR MDAS_AR_DOMN_ACCESS_ERROR");
		srbException.setProperty("-4084",
				"MDAS_AR_AUTH_KEY_ACCESS_ERROR MDAS_AR_AUTH_KEY_ACCESS_ERROR");
		srbException.setProperty("-4085",
				"MDAS_AR_DECR_KEY_ACCESS_ERROR MDAS_AR_DECR_KEY_ACCESS_ERROR");
		srbException.setProperty("-4086",
				"MDAS_AR_REPL_ACCESS_ERROR MDAS_AR_REPL_ACCESS_ERROR");
		srbException.setProperty("-4087",
				"MDAS_AR_COMMENTS_ACCESS_ERROR MDAS_AR_COMMENTS_ACCESS_ERROR");
		srbException.setProperty("-4088",
				"MDAS_AR_LOCK_ACCESS_ERROR MDAS_AR_LOCK_ACCESS_ERROR");
		srbException.setProperty("-4089",
				"MDAS_AR_ACCS_ACCESS_ERROR MDAS_AR_ACCS_ACCESS_ERROR");
		srbException.setProperty("-4090",
				"MDAS_AR_SUMM_ACCESS_ERROR MDAS_AR_SUMM_ACCESS_ERROR");
		srbException.setProperty("-4091",
				"MDAS_AR_AUDIT_ACCESS_ERROR MDAS_AR_AUDIT_ACCESS_ERROR");
		srbException.setProperty("-4092",
				"MDAS_AR_LIN_RSRC_ACCESS_ERROR MDAS_AR_LIN_RSRC_ACCESS_ERROR");
		srbException.setProperty("-4093",
				"MDAS_AR_LIN_METH_ACCESS_ERROR MDAS_AR_LIN_METH_ACCESS_ERROR");
		srbException.setProperty("-4094",
				"MDAS_AR_LIN_USER_ACCESS_ERROR MDAS_AR_LIN_USER_ACCESS_ERROR");
		srbException.setProperty("-4095",
				"MDAS_AR_LIN_PARM_ACCESS_ERROR MDAS_AR_LIN_PARM_ACCESS_ERROR");
		srbException.setProperty("-4096",
				"MDAS_AR_LIN_DATA_ACCESS_ERROR MDAS_AR_LIN_DATA_ACCESS_ERROR");
		srbException.setProperty("-4097",
				"MDAS_AR_FON_RSRC_ACCESS_ERROR MDAS_AR_FON_RSRC_ACCESS_ERROR");
		srbException
				.setProperty("-4098",
						"MDAS_AR_APPL_PRED_ACCESS_ERROR MDAS_AR_APPL_PRED_ACCESS_ERROR");
		srbException.setProperty("-4099",
				"MDAS_AR_SD_ACCESS_ERROR MDAS_AR_SD_ACCESS_ERROR");
		srbException.setProperty("-4100",
				"MDAS_AR_TYP_SD_ACCESS_ERROR MDAS_AR_TYP_SD_ACCESS_ERROR");
		srbException.setProperty("-4101",
				"MDAS_AU_INFO_ACCESS_ERROR MDAS_AU_INFO_ACCESS_ERROR");
		srbException.setProperty("-4102",
				"MDAS_AU_ALIAS_ACCESS_ERROR MDAS_AU_ALIAS_ACCESS_ERROR");
		srbException.setProperty("-4103",
				"MDAS_AU_GROUP_ACCESS_ERROR MDAS_AU_GROUP_ACCESS_ERROR");
		srbException.setProperty("-4104",
				"MDAS_AU_DOMN_ACCESS_ERROR MDAS_AU_DOMN_ACCESS_ERROR");
		srbException.setProperty("-4105",
				"MDAS_AU_AUTH_KEY_ACCESS_ERROR MDAS_AU_AUTH_KEY_ACCESS_ERROR");
		srbException.setProperty("-4106",
				"MDAS_AU_DECR_KEY_ACCESS_ERROR MDAS_AU_DECR_KEY_ACCESS_ERROR");
		srbException.setProperty("-4107",
				"MDAS_AU_SUMM_ACCESS_ERROR MDAS_AU_SUMM_ACCESS_ERROR");
		srbException.setProperty("-4108",
				"MDAS_AU_AUDIT_ACCESS_ERROR MDAS_AU_AUDIT_ACCESS_ERROR");
		srbException.setProperty("-4109",
				"MDAS_AU_SD_ACCESS_ERROR MDAS_AU_SD_ACCESS_ERROR");
		srbException.setProperty("-4110",
				"MDAS_AU_TYP_SD_ACCESS_ERROR MDAS_AU_TYP_SD_ACCESS_ERROR");
		srbException.setProperty("-4111",
				"MDAS_COUNTER_ACCESS_ERROR MDAS_COUNTER_ACCESS_ERROR");
		srbException.setProperty("-4112",
				"MDAS_SD_KEYWORD_ACCESS_ERROR MDAS_SD_KEYWORD_ACCESS_ERROR");
		srbException
				.setProperty("-4113",
						"MDAS_TD_DATA_ACCS_ACCESS_ERROR MDAS_TD_DATA_ACCS_ACCESS_ERROR");
		srbException.setProperty("-4114",
				"MDAS_AD_ACCESS_ACCESS_ERROR MDAS_AD_ACCESS_ACCESS_ERROR");
		srbException.setProperty("-4115",
				"MDAS_AD_GRP_ACCS_ACCESS_ERROR MDAS_AD_GRP_ACCS_ACCESS_ERROR");
		srbException.setProperty("-4116",
				"MDAS_AR_PHYSICAL_ACCESS_ERROR MDAS_AR_PHYSICAL_ACCESS_ERROR");
		srbException
				.setProperty("-4117",
						"MDAS_AU_TCKT_DATA_ACCESS_ERROR MDAS_AU_TCKT_DATA_ACCESS_ERROR");
		srbException.setProperty("-4118",
				"MDAS_AU_TCKT_GRP_ACCESS_ERROR MDAS_AU_TCKT_GRP_ACCESS_ERROR");
		srbException.setProperty("-4119",
				"MDAS_AR_DB_RSRC_ACCESS_ERROR MDAS_AR_DB_RSRC_ACCESS_ERROR");
		srbException
				.setProperty("-4120",
						"MDAS_TD_CONTAINER_ACCESS_ERROR MDAS_TD_CONTAINER_ACCESS_ERROR");
		srbException
				.setProperty("-4121",
						"MDAS_TD_RSRC_CLASS_ACCESS_ERROR MDAS_TD_RSRC_CLASS_ACCESS_ERROR");
		srbException.setProperty("-4122",
				"MDAS_AU_AUTH_MAP_ACCESS_ERROR MDAS_AU_AUTH_MAP_ACCESS_ERROR");
		srbException.setProperty("-4123",
				"MDAS_AR_AUTH_ACCESS_ERROR MDAS_AR_AUTH_ACCESS_ERROR");
		srbException
				.setProperty("-4124",
						"MDAS_TD_AUTH_SCHM_ACCESS_ERROR MDAS_TD_AUTH_SCHM_ACCESS_ERROR");
		srbException
				.setProperty("-4125",
						"MDAS_AD_ANNOTATION_ACCESS_ERROR MDAS_AD_ANNOTATION_ACCESS_ERROR");
		srbException.setProperty("-4126",
				"MDAS_AD_CLASS_ACCESS_ERROR MDAS_AD_CLASS_ACCESS_ERROR");
		srbException
				.setProperty("-4127",
						"MDAS_AD_DATA_INDEX_ACCESS_ERROR MDAS_AD_DATA_INDEX_ACCESS_ERROR");
		srbException
				.setProperty("-4128",
						"MDAS_AD_DTYP_INDEX_ACCESS_ERROR MDAS_AD_DTYP_INDEX_ACCESS_ERROR");
		srbException
				.setProperty("-4129",
						"MDAS_AD_COLL_INDEX_ACCESS_ERROR MDAS_AD_COLL_INDEX_ACCESS_ERROR");
		srbException
				.setProperty("-4130",
						"MDAS_AD_DATA_METH_ACCESS_ERROR MDAS_AD_DATA_METH_ACCESS_ERROR");
		srbException
				.setProperty("-4131",
						"MDAS_AD_DTYP_METH_ACCESS_ERROR MDAS_AD_DTYP_METH_ACCESS_ERROR");
		srbException
				.setProperty("-4132",
						"MDAS_AD_COLL_METH_ACCESS_ERROR MDAS_AD_COLL_METH_ACCESS_ERROR");
		srbException
				.setProperty("-4133",
						"MDAS_AD_STRCT_BLOB_ACCESS_ERROR MDAS_AD_STRCT_BLOB_ACCESS_ERROR");
		srbException.setProperty("-4134",
				"MDAS_AD_MDATA_ACCESS_ERROR MDAS_AD_MDATA_ACCESS_ERROR");
		srbException.setProperty("-4135",
				"MDAS_AD_COLLCONT_ACCESS_ERROR MDAS_AD_COLLCONT_ACCESS_ERROR");
		srbException
				.setProperty("-4136",
						"MDAS_AD_COLLMDATA_ACCESS_ERROR MDAS_AD_COLLMDATA_ACCESS_ERROR");
		srbException
				.setProperty("-4137",
						"MDAS_AC_ANNOTATION_ACCESS_ERROR MDAS_AC_ANNOTATION_ACCESS_ERROR");
		srbException
				.setProperty("-4138",
						"MDAS_AR_CMPND_RSRC_ACCESS_ERROR MDAS_AR_CMPND_RSRC_ACCESS_ERROR");
		srbException.setProperty("-4139",
				"MDAS_AD_COMPOUND_ACCESS_ERROR MDAS_AD_COMPOUND_ACCESS_ERROR");
		srbException
				.setProperty("-4140",
						"MDAS_AR_TAPE_INFO_ACCESS_ERROR MDAS_AR_TAPE_INFO_ACCESS_ERROR");
		srbException.setProperty("-4141",
				"MDAS_AD_MISC1_ACCESS_ERROR MDAS_AD_MISC1_ACCESS_ERROR");
		srbException.setProperty("-4142",
				"MDAS_AU_MDATA_ACCESS_ERROR MDAS_AU_MDATA_ACCESS_ERROR");
		srbException.setProperty("-4143",
				"MDAS_AR_MDATA_ACCESS_ERROR MDAS_AR_MDATA_ACCESS_ERROR");
		srbException.setProperty("-4144",
				"MDAS_TD_ZONE_ACCESS_ERROR MDAS_TD_ZONE_ACCESS_ERROR");
		srbException.setProperty("-4145",
				"MDAS_AD_GUID_ACCESS_ERROR MDAS_AD_GUID_ACCESS_ERROR");
		srbException.setProperty("-4146",
				"MDAS_AR_INFO_ACCESS_ERROR MDAS_AR_INFO_ACCESS_ERROR");
		srbException
				.setProperty("-4147",
						"MDAS_AR_DAILY_TOT_ACCESS_ERROR MDAS_AR_DAILY_TOT_ACCESS_ERROR");
		srbException
				.setProperty("-4148",
						"MDAS_AR_USE_QUOTA_ACCESS_ERROR MDAS_AR_USE_QUOTA_ACCESS_ERROR");
		srbException.setProperty("-4501",
				"ORA_AUTOCOMMIT_TURNOFF_ERROR ORA_AUTOCOMMIT_TURNOFF_ERROR");
		srbException
				.setProperty("-4502", "ORA_CONNECT_ERROR ORA_CONNECT_ERROR");
		srbException.setProperty("-4503",
				"ORA_TRANSACT_CLOSE_ERROR ORA_TRANSACT_CLOSE_ERROR");
		srbException.setProperty("-4504",
				"ORA_DISCONNECT_ERROR ORA_DISCONNECT_ERROR");
		srbException.setProperty("-4505",
				"ORA_CURSOR_OPEN_ERROR ORA_CURSOR_OPEN_ERROR");
		srbException.setProperty("-4506", "ORA_PARSE_ERROR ORA_PARSE_ERROR");
		srbException.setProperty("-4507", "ORA_EXEC_ERROR ORA_EXEC_ERROR");
		srbException.setProperty("-4508",
				"ORA_SELECT_DESCRIBE_ERROR ORA_SELECT_DESCRIBE_ERROR");
		srbException.setProperty("-4509", "ORA_FETCH_ERROR ORA_FETCH_ERROR");
		srbException
				.setProperty("-4510", "ORA_NO_DATA_FOUND ORA_NO_DATA_FOUND");
		srbException.setProperty("-4511",
				"ORA_DATA_LINK_CREATION_ERROR ORA_DATA_LINK_CREATION_ERROR");
		srbException.setProperty("-5000",
				"TAPLIB_MNT_CART_ERROR TAPLIB_MNT_CART_ERROR");
		srbException.setProperty("-5001",
				"TAPLIB_INTERNAL_ERROR TAPLIB_INTERNAL_ERROR");
		srbException.setProperty("-5002", "TAPLIB_INX_ERROR TAPLIB_INX_ERROR");
		srbException.setProperty("-5003",
				"TAPLIB_NO_TAPE_RESC TAPLIB_NO_TAPE_RESC");
		srbException.setProperty("-5004",
				"TAPLIB_NO_BLANK_TAPE TAPLIB_NO_BLANK_TAPE");
		srbException.setProperty("-5005",
				"TAPLIB_SET_ACCESS_FAILED TAPLIB_SET_ACCESS_FAILED");
		srbException.setProperty("-5006",
				"TAPLIB_CART_TYPE_NOT_EXIST TAPLIB_CART_TYPE_NOT_EXIST");
		srbException.setProperty("-5007",
				"TAPLIB_CART_LOC_ERROR TAPLIB_CART_LOC_ERROR");
		srbException.setProperty("-5008",
				"TAPLIB_CART_MISMATCH_FOR_OPR TAPLIB_CART_MISMATCH_FOR_OPR");
		srbException
				.setProperty("-5200",
						"TAPE_NUM_ERROR TAPE_NUM_ERROR Error with the length of the tape number");
		srbException
				.setProperty(
						"-5201",
						"TAPE_FILE_SEG_NUM_ERROR TAPE_FILE_SEG_NUM_ERROR Error with the size of the tape segment number");
		srbException
				.setProperty(
						"-5202",
						"TAPE_FILE_SEQ_NUM_ERROR TAPE_FILE_SEQ_NUM_ERROR Error with the size of the tape sequent number");
		srbException.setProperty("-5203",
				"TAPE_FILE_NOT_OPENED TAPE_FILE_NOT_OPENED");
		srbException.setProperty("-5204",
				"TAPE_BLOCK_SZ_ERROR TAPE_BLOCK_SZ_ERROR");
		srbException.setProperty("-5205",
				"TAPE_VOL_LABEL_ERROR TAPE_VOL_LABEL_ERROR");
		srbException.setProperty("-5206",
				"TAPE_HEADER_LABEL_ERROR TAPE_HEADER_LABEL_ERROR");
		srbException.setProperty("-5207",
				"TAPE_FILE_POSITION_ERROR TAPE_FILE_POSITION_ERROR");
		srbException.setProperty("-5208",
				"TAPE_DEVICE_PATH_ERROR TAPE_DEVICE_PATH_ERROR");
		srbException.setProperty("-5209",
				"TAPE_TAPELIB_CONFIG_ERROR TAPE_TAPELIB_CONFIG_ERROR");
		srbException.setProperty("-5210",
				"TAPE_DEVICE_INX_ERROR TAPE_DEVICE_INX_ERROR");
		srbException.setProperty("-5211",
				"TAPE_CONFIG_ERROR Internal tape config error");
		srbException.setProperty("-5212",
				"TAPE_OUT_OF_BLANK_TAPE TAPE_OUT_OF_BLANK_TAPE");
		srbException
				.setProperty("-5400", "ADS_UNKNOWN_ERROR ADS_UNKNOWN_ERROR");
		srbException.setProperty("-5401",
				"ADS_ERROR_FUNC_NOT_SUPPORTED ADS_ERROR_FUNC_NOT_SUPPORTED");
		srbException
				.setProperty("-5402",
						"ADS_ERROR_EXECUTING_PIPED_COMMAND0 ADS_ERROR_EXECUTING_PIPED_COMMAND0");
		srbException.setProperty("-5403",
				"ADS_ERROR_IN_PATHTAPE ADS_ERROR_IN_PATHTAPE ");
		srbException.setProperty("-5404",
				"ADS_ERROR_IN_FLFSYS_CREATE ADS_ERROR_IN_FLFSYS_CREATE ");
		srbException.setProperty("-5405",
				"ADS_ERROR_IN_FLFSYS_DESTROY ADS_ERROR_IN_FLFSYS_DESTROY ");
		srbException
				.setProperty("-5406",
						"ADS_ERROR_EXECUTING_PIPED_COMMAND ADS_ERROR_EXECUTING_PIPED_COMMAND");
		srbException
				.setProperty("-5407", "ADS_ERROR_ON_OPEN ADS_ERROR_ON_OPEN");
		srbException.setProperty("-5408",
				"ADS_ERROR_ON_CLOSE ADS_ERROR_ON_CLOSE");
		srbException.setProperty("-5409",
				"ADS_ERROR_IN_VTP_OPEN ADS_ERROR_IN_VTP_OPEN");
		srbException.setProperty("-5410",
				"ADS_ERROR_IN_VTP_CLOSE ADS_ERROR_IN_VTP_CLOSE");
		srbException.setProperty("-5411",
				"ADS_ERROR_WRITING_TAPE_LABELS ADS_ERROR_WRITING_TAPE_LABELS");
		srbException.setProperty("-5412",
				"ADS_ERROR_READING_TAPE_LABELS ADS_ERROR_READING_TAPE_LABELS");
		srbException.setProperty("-5413",
				"ADS_ERROR_IN_TAPE_READ ADS_ERROR_IN_TAPE_READ");
		srbException.setProperty("-5414",
				"ADS_ERROR_IN_TAPE_WRITE ADS_ERROR_IN_TAPE_WRITE");
		srbException.setProperty("-5415",
				"ADS_ERROR_ALLOCATING_MEMORY ADS_ERROR_ALLOCATING_MEMORY");
		srbException.setProperty("-6001",
				"GRIDFTP_OPEN_NULL_URL GRIDFTP_OPEN_NULL_URL");
		srbException.setProperty("-6003",
				"GRIDFTP_CANNOT_CALLOC GRIDFTP_CANNOT_CALLOC");
		srbException.setProperty("-6004",
				"GRIDFTP_CANNOT_FIND_NODE GRIDFTP_CANNOT_FIND_NODE");
		srbException.setProperty("-6005",
				"GRIDFTP_GETHOSTNAME_FAILED GRIDFTP_GETHOSTNAME_FAILED");
		srbException.setProperty("-6006",
				"GRIDFTP_GETHOSTBYNAME_FAILED GRIDFTP_GETHOSTBYNAME_FAILED");
		srbException.setProperty("-6007",
				"GRIDFTP_NOT_A_WRITE_OPERATION GRIDFTP_NOT_A_WRITE_OPERATION");
		srbException.setProperty("-6008",
				"GRIDFTP_CLIENT_GET_ERROR GRIDFTP_CLIENT_GET_ERROR");
		srbException.setProperty("-6009",
				"GRIDFTP_READ_DONE_CB_ERR GRIDFTP_READ_DONE_CB_ERR");
		srbException.setProperty("-6010",
				"GRIDFTP_CLIENT_PUT_ERROR GRIDFTP_CLIENT_PUT_ERROR");
		srbException.setProperty("-6011",
				"GRIDFTP_WRITE_DONE_CB_ERR GRIDFTP_WRITE_DONE_CB_ERR");
		srbException.setProperty("-6012",
				"GRIDFTP_CLIENT_MKDIR_ERROR GRIDFTP_CLIENT_MKDIR_ERROR");
		srbException.setProperty("-6013",
				"GRIDFTP_MKDIR_DONE_CB_ERR GRIDFTP_MKDIR_DONE_CB_ERR");
		srbException.setProperty("-6014",
				"GRIDFTP_CLIENT_RMDIR_ERROR GRIDFTP_CLIENT_RMDIR_ERROR");
		srbException.setProperty("-6015",
				"GRIDFTP_RMDIR_DONE_CB_ERR GRIDFTP_RMDIR_DONE_CB_ERR");
		srbException.setProperty("-6016",
				"GRIDFTP_CLIENT_UNLINK_ERROR GRIDFTP_CLIENT_UNLINK_ERROR");
		srbException.setProperty("-6017",
				"GRIDFTP_UNLINK_DONE_CB_ERR GRIDFTP_UNLINK_DONE_CB_ERR");
		srbException.setProperty("-6018",
				"GRIDFTP_CLIENT_EXISTS_ERROR GRIDFTP_CLIENT_EXISTS_ERROR");
		srbException
				.setProperty("-6019",
						"GRIDFTP_FILE_OR_DIR_EXISTS_DONE_CB_ERR GRIDFTP_FILE_OR_DIR_EXISTS_DONE_CB_ERR");
		srbException.setProperty("-6020",
				"GRIDFTP_CLIENT_SIZE_ERROR GRIDFTP_CLIENT_SIZE_ERROR");
		srbException.setProperty("-6021",
				"GRIDFTP_SIZE_DONE_CB_ERR GRIDFTP_SIZE_DONE_CB_ERR");
		srbException.setProperty("-6022",
				"GRIDFTP_OPERATION_NOT_SUPPORT GRIDFTP_OPERATION_NOT_SUPPORT");
		srbException
				.setProperty("-6023",
						"GRIDFTP_NEED_NEW_PROXY_CERTIFICATE GRIDFTP_NEED_NEW_PROXY_CERTIFICATE");
		srbException.setProperty("-6024",
				"GRIDFTP_NO_USER_CERT_ERR GRIDFTP_NO_USER_CERT_ERR");
		srbException.setProperty("-6025",
				"GRIDFTP_NO_FREE_GFTP_CONN_ERR GRIDFTP_NO_FREE_GFTP_CONN_ERR");
		srbException
				.setProperty("-6026",
						"GRIDFTP_GFTP_CONN_OUT_OF_RANG_ERR GRIDFTP_GFTP_CONN_OUT_OF_RANG_ERR");
		srbException.setProperty("-6027",
				"GRIDFTP_BAD_INPUT_URL_ERR GRIDFTP_BAD_INPUT_URL_ERR");
		srbException.setProperty("-999999", "DONT_SEND_RETURN");
		srbException.setProperty("-9999999", "EXIT_WHEN_DONE");
		srbException
				.setProperty("-99999999",
						"MSG_USE_SINGLE_PORT  Using single port, instead of parallel I/O.");
		// MAGIC_LINE (DONT CHANGE THIS LINE - it has to come above the
		// following static declarations);
	}
}
