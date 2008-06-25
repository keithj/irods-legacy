/*** Copyright (c), The Regents of the University of California            ***
 *** For more information please refer to files in the COPYRIGHT directory ***/
/* fileLseek.h - This file may be generated by a program or script
 */

#ifndef DATA_OBJ_LSEEK_H
#define DATA_OBJ_LSEEK_H

/* This is a low level file type API call */

#include "rods.h"
#include "rcMisc.h"
#include "procApiRequest.h"
#include "apiNumber.h"
#include "initServer.h"

#include "fileDriver.h"
#include "fileLseek.h"

/* This is a high level type API call */

#if defined(RODS_SERVER)
#define RS_DATA_OBJ_LSEEK rsDataObjLseek
/* prototype for the server handler */
int
rsDataObjLseek (rsComm_t *rsComm, fileLseekInp_t *dataObjLseekInp, 
fileLseekOut_t **dataObjLseekOut);
rodsLong_t
_l3Lseek (rsComm_t *rsComm, int rescTypeInx, int l3descInx,
rodsLong_t offset, int whence);
#else
#define RS_DATA_OBJ_LSEEK NULL
#endif

#ifdef  __cplusplus
extern "C" {
#endif

/* prototype for the client call */
/* rcDataObjLseek - Lseek an opened iRods data object descriptor.
 * Input -
 *   rcComm_t *conn - The client connection handle.
 *   fileLseekInp_t *dataObjLseekInp - Relevant items are:
 *      l1descInx - the iRods data object descriptor to lseek.
 *	offset - the offset
 *	whence - SEEK_SET, SEEK_CUR and SEEK_END
 *
 * OutPut -
 *   irodsLong_t status. If >= 0, the offset. < 0 ==> error.
 */

int
rcDataObjLseek (rcComm_t *conn, fileLseekInp_t *dataObjLseekInp,
fileLseekOut_t **dataObjLseekOut);

#ifdef  __cplusplus
}
#endif

#endif	/* DATA_OBJ_LSEEK_H */
