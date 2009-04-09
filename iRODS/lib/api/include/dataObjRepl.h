/*** Copyright (c), The Regents of the University of California            ***
 *** For more information please refer to files in the COPYRIGHT directory ***/
/* dataObjRepl.h - This dataObj may be generated by a program or script
 */

#ifndef DATA_OBJ_REPL_H
#define DATA_OBJ_REPL_H

/* This is a high level type API call */

#include "rods.h"
#include "rcMisc.h"
#include "procApiRequest.h"
#include "apiNumber.h"
#include "initServer.h"
#include "dataObjWrite.h"
#include "dataObjClose.h"
#include "dataCopy.h"

#if defined(RODS_SERVER)
#define RS_DATA_OBJ_REPL rsDataObjRepl
/* prototype for the server handler */
int
rsDataObjRepl (rsComm_t *rsComm, dataObjInp_t *dataObjInp, 
transStat_t **transStat);
int
rsDataObjReplWithOutDataObj (rsComm_t *rsComm, dataObjInp_t *dataObjInp,
transStat_t *transStat, dataObjInfo_t *outDataObjInfo);
int
_rsDataObjRepl (rsComm_t *rsComm, dataObjInp_t *dataObjInp,
dataObjInfo_t *srcDataObjInfoHead, rescGrpInfo_t *destRescGrpInfo,
transStat_t *transStat, dataObjInfo_t *destDataObjInfo);
int
_rsDataObjReplS (rsComm_t *rsComm, dataObjInp_t *dataObjInp,
dataObjInfo_t *srcDataObjInfo, rescInfo_t *destRescInfo, 
char *rescGroupName, dataObjInfo_t *destDataObjInfo);
int
dataObjOpenForRepl (rsComm_t *rsComm, dataObjInp_t *dataObjInp,
dataObjInfo_t *srcDataObjInfo, rescInfo_t *destRescInfo, 
char *rescGroupName, dataObjInfo_t *destDataObjInfo);
int
dataObjCopy (rsComm_t *rsComm, int l1descInx);
int
l3DataCopySingleBuf (rsComm_t *rsComm, int l1descInx);
int
l3DataStageSync (rsComm_t *rsComm, int l1descInx);
int
l3FileSync (rsComm_t *rsComm, int srcL1descInx, int destL1descInx);
int
l3FileStage (rsComm_t *rsComm, int srcL1descInx, int destL1descInx);
int
rsReplAndRequeDataObjInfo (rsComm_t *rsComm, 
dataObjInfo_t **srcDataObjInfoHead, char *destRescName, char *flagStr);
int
replToCacheRescOfCompObj (rsComm_t *rsComm, dataObjInp_t *dataObjInp,
dataObjInfo_t *srcDataObjInfoHead, dataObjInfo_t *compObjInfo,
dataObjInfo_t **outDestDataObjInfo);
#else
#define RS_DATA_OBJ_REPL NULL
#endif

#ifdef  __cplusplus
extern "C" {
#endif

/* prototype for the client call */
/* rcDataObjRepl - Replicate an iRods data object.
 * Input -
 *   rcComm_t *conn - The client connection handle.
 *   dataObjInp_t *dataObjInp - generic dataObj input. Relevant items are:
 *      objPath - the path of the data object.
 *      condInput - conditional Input
 *          ALL_KW - update all copies.
 *          DATA_TYPE_KW - "value" = the data type of the file.
 *          REPL_NUM_KW  - "value" = The replica number to use as source
 *              copy. (optional)
 *          RESC_NAME_KW - "value" = The source Resource (optional). 
 *          DEST_RESC_NAME_KW - "value" = The destination Resource. 
 *          IRODS_ADMIN_KW - Admin removing other users' files. Only files
 *	        in trash can be removed.
 *	    BACKUP_RESC_NAME_KW - backup resource (backup mode).
 *   return value - The status of the operation.
 */
int
rcDataObjRepl (rcComm_t *conn, dataObjInp_t *dataObjInp);
int
_rcDataObjRepl (rcComm_t *conn, dataObjInp_t *dataObjInp,
transStat_t **transStat);
int
stageDataFromCompToCache (rsComm_t *rsComm, dataObjInfo_t *compObjInfo,
dataObjInfo_t *outCacheObjInfo);
int
stageAndRequeDataToCache (rsComm_t *rsComm, dataObjInfo_t **compObjInfoHead);
int
stageBundledData (rsComm_t *rsComm, dataObjInfo_t **subfileObjInfoHead);
#ifdef  __cplusplus
}
#endif

#endif	/* DATA_OBJ_REPL_H */
