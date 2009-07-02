/*** Copyright (c), The Regents of the University of California            ***
 *** For more information please refer to files in the COPYRIGHT directory ***/
/* dataObjPut.h - This dataObj may be generated by a program or script
 */

#ifndef DATA_OBJ_PUT_H
#define DATA_OBJ_PUT_H

/* This is a high level type API call */

#include "rods.h"
#include "rcMisc.h"
#include "procApiRequest.h"
#include "apiNumber.h"
#include "initServer.h"
#include "dataObjWrite.h"
#include "dataObjClose.h"

#if defined(RODS_SERVER)
#define RS_DATA_OBJ_PUT rsDataObjPut
/* prototype for the server handler */
int
rsDataObjPut (rsComm_t *rsComm, dataObjInp_t *dataObjInp, 
bytesBuf_t *dataObjInpBBuf, portalOprOut_t **portalOprOut);
int
_rsDataObjPut (rsComm_t *rsComm, dataObjInp_t *dataObjInp,
bytesBuf_t *dataObjInpBBuf, portalOprOut_t **portalOprOut, int handlerFlag);
int
l2DataObjPut (rsComm_t *rsComm, int l1descInx, 
portalOprOut_t **portalOprOut);
int
l3DataPutSingleBuf (rsComm_t *rsComm, dataObjInp_t *dataObjInp,
bytesBuf_t *dataObjInpBBuf);
int
l3FilePutSingleBuf (rsComm_t *rsComm, int l1descInx, bytesBuf_t *dataObjInpBBuf);
#else
#define RS_DATA_OBJ_PUT NULL
#endif

#ifdef  __cplusplus
extern "C" {
#endif

/* prototype for the client call */
/* rcDataObjPut - Put (upload) a local file to iRODS.
 * Input -
 *   rcComm_t *conn - The client connection handle.
 *   dataObjInp_t *dataObjInp - generic dataObj input. Relevant items are:
 *      objPath - the path of the data object.
 *	numThreads - Number of threads to use. NO_THREADING ==> no threading,
 *	   0 ==> server will decide (default), >0 ==> number of threads.  
 *      openFlags - should be set to O_WRONLY.
 *      condInput - conditional Input
 *          FORCE_FLAG_KW - overwrite an existing data object
 *	    ALL_KW - update all copies.
 *	    DATA_TYPE_KW - "value" = the data type of the file.	
 *          REPL_NUM_KW  - "value" = The replica number of the copy to
 *              upload.
 *          FILE_PATH_KW - "value" = the physical path of the
 *              destination file. Vaild only if O_CREAT is on.
 *          DEST_RESC_NAME_KW - "value" = The destination Resource. Vaild
 *              only if O_CREAT is on.
 *   return value - The status of the operation.
 */

int
rcDataObjPut (rcComm_t *conn, dataObjInp_t *dataObjInp,
char *locFilePath);
int
_rcDataObjPut (rcComm_t *conn, dataObjInp_t *dataObjInp,
bytesBuf_t *dataObjInpBBuf, portalOprOut_t **portalOprOut);
#ifdef  __cplusplus
}
#endif

#endif	/* DATA_OBJ_PUT_H */
