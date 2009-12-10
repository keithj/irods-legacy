
/*** Copyright (c), The Regents of the University of California            ***
 *** For more information please refer to files in the COPYRIGHT directory ***/
/*****************************************************************************/

#ifndef ICAT_HIGHLEVEL_ROUTINES_H
#define ICAT_HIGHLEVEL_ROUTINES_H

#include "objInfo.h"
#include "ruleExecSubmit.h"
#include "rcConnect.h"
#include "rodsGeneralUpdate.h"

int chlOpen(char *DBUser, char *DBpasswd);
int chlClose();
int chlIsConnected();
int chlModDataObjMeta(rsComm_t *rsComm, dataObjInfo_t *dataObjInfo,
    keyValPair_t *regParam);
int chlRegDataObj(rsComm_t *rsComm, dataObjInfo_t *dataObjInfo);
int chlRegRuleExecObj(rsComm_t *rsComm,
		      ruleExecSubmitInp_t *ruleExecSubmitInp);
int chlRegReplica(rsComm_t *rsComm, dataObjInfo_t *srcDataObjInfo,
    dataObjInfo_t *dstDataObjInfo, keyValPair_t *condInput);
int chlUnregDataObj (rsComm_t *rsComm, dataObjInfo_t *dataObjInfo, 
    keyValPair_t *condInput);
int chlRegResc(rsComm_t *rsComm, rescInfo_t *rescInfo);
int chlDelResc(rsComm_t *rsComm, rescInfo_t *rescInfo);
int chlRollback(rsComm_t *rsComm);
int chlCommit(rsComm_t *rsComm);
int chlDelUserRE(rsComm_t *rsComm, userInfo_t *userInfo);
int chlRegCollByAdmin(rsComm_t *rsComm, collInfo_t *collInfo);
int chlRegColl(rsComm_t *rsComm, collInfo_t *collInfo);
int chlModColl(rsComm_t *rsComm, collInfo_t *collInfo);

int chlSimpleQuery(rsComm_t *rsComm, char *sql, 
    char *arg1, char *arg2, char *arg3, char *arg4, 
    int format, 
    int *control, char *outBuf, int maxOutBuf);
int chlGenQuery(genQueryInp_t genQueryInp, genQueryOut_t *result);
int chlGenQueryAccessControlSetup(char *user, char *zone, int priv, 
    int controlFlag);

int chlGeneralUpdate(generalUpdateInp_t generalUpdateInp);

int chlDelCollByAdmin(rsComm_t *rsComm, collInfo_t *collInfo);
int chlDelColl(rsComm_t *rsComm, collInfo_t *collInfo);
int chlCheckAuth(rsComm_t *rsComm, char *challenge, char *response,
    char *username, int *userPrivLevel, int *clientPrivLevel);
int chlMakeTempPw(rsComm_t *rsComm, char *pwValueToHash);
int decodePw(rsComm_t *rsComm, char *in, char *out);
int chlModUser(rsComm_t *rsComm, char *userName, char *option,
    char *newValue);
int chlModGroup(rsComm_t *rsComm, char *groupName, char *option,
    char *userName, char *userZone);
int chlModResc(rsComm_t *rsComm, char *rescName, char *option,
    char *optionValue);
int chlModRescFreeSpace(rsComm_t *rsComm, char *rescName, 
    int updateValue);
int chlRegUserRE(rsComm_t *rsComm, userInfo_t *userInfo);
rodsLong_t checkAndGetObjectId(rsComm_t *rsComm, char *type,
    char *name, char *access);
int chlAddAVUMetadata(rsComm_t *rsComm, int adminMode, char *type, 
    char *name, char *attribute, char *value,  char *units);
int chlDeleteAVUMetadata(rsComm_t *rsComm, int option, char *type, 
    char *name, char *attribute, char *value,  char *units, int noCommit);
int chlCopyAVUMetadata(rsComm_t *rsComm, char *type1,  char *type2, 
    char *name1, char *name2);
int chlModAVUMetadata(rsComm_t *rsComm, char *type, char *name, 
		      char *attribute, char *value, char *unitsOrChange0,
		      char *change1, char *change2, char *change3);
int chlModAccessControl(rsComm_t *rsComm, int recursiveFlag,
			char* accessLevel, char *userName, char *zone, 
			char* pathName);

int chlModRescGroup(rsComm_t *rsComm, char *rescGroupName, char *option,
		    char *rescName);

int chlRegRuleExec(rsComm_t *rsComm, ruleExecSubmitInp_t *ruleExecSubmitInp);
int chlModRuleExec(rsComm_t *rsComm, char *ruleExecId, keyValPair_t *regParam);
int chlDelRuleExec(rsComm_t *rsComm, char *ruleExecId);

int chlRenameObject(rsComm_t *rsComm, rodsLong_t objId, char *newName);
int chlMoveObject(rsComm_t *rsComm, rodsLong_t objId, rodsLong_t targetCollId);

int chlRegToken(rsComm_t *rsComm, char *nameSpace, char *name, char *value,
		char *value2, char *value3, char *comment);
int chlDelToken(rsComm_t *rsComm, char *nameSpace, char *Name);

int chlRegZone(rsComm_t *rsComm, char *zoneName, char *zoneType, 
	       char *zoneConnInfo, char *zoneComment);
int chlModZone(rsComm_t *rsComm, char *zoneName, char *option,
	       char *optionValue);
int chlDelZone(rsComm_t *rsComm, char *zoneName);
int chlRenameLocalZone(rsComm_t *rsComm, char *oldZoneName, char *newZoneName);
int chlRenameColl(rsComm_t *rsComm, char *oldName, char *newName);

int chlRegServerLoad(rsComm_t *rsComm, 
    char *hostName, char *rescName,
    char *cpuUsed, char *memUsed, char *swapUsed, char *runqLoad,
    char *diskSpace, char *netInput, char *netOutput);
int chlPurgeServerLoad(rsComm_t *rsComm, char *secondsAgo);
int chlRegServerLoadDigest(rsComm_t *rsComm, char *rescName, char *loadFactor);
int chlPurgeServerLoadDigest(rsComm_t *rsComm, char *secondsAgo);

int chlCalcUsage(rsComm_t *rsComm);
int chlSetQuota(rsComm_t *rsComm, char *type, char *name, char *rescName,
   char *limit);

char *chlGetLocalZone();

int sTableInit();
int sFklink(char *table1, char *table2, char *connectingSQL);
int sTable(char *tableName, char *tableAlias, int cycler);
int sColumn(int defineVal, char *tableName, char *columnName);

int chlDebug(char *debugMode);
int chlDebugGenQuery(int mode);
int chlDebugGenUpdate(int mode);

#endif /* ICAT_HIGHLEVEL_ROUTINES_H */
