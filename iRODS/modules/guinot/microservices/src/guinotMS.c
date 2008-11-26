
/**
 * @file	guinotMS.c
 *
 * @brief	Microservices contributed by Romain Guinot
 *
 * To be added later to core afterwards
 * 
 *
 * @author	Romain Guinot
 */

#include "rsApiHandler.h"
#include "guinotMS.h"

/*
 Convert a Unix time value (as from getNowStr) to a local 
 time format.
*/
int
getFormattedLocalTimeFromRodsTime(char *timeStrIn, 
				  char *timeStr,
				  const char *timeFormat) 
{
  time_t myTime;
  struct tm *mytm;
  if (sizeof(time_t)==4) {
    mytm = localtime (&myTime);
    snprintf (timeStr, TIME_LEN, timeFormat,
	      mytm->tm_year + 1900, mytm->tm_mon + 1, mytm->tm_mday, 
	      mytm->tm_hour, mytm->tm_min, mytm->tm_sec);
    return(0);
  }
}


/**
 * \fn msiGetFormattedSystemTime
 * \author  Romain Guinot
 * \date   2008
 * \brief This microservice returns the local system time
 * \note Default output format is system time in seconds, use 'human' as input 
         aram for human readable format.
 * \param[in] 
 *    inpParam - Optional - a STR_MS_T containing the desired output format
 * \param[out] 
 *    outParam - a STR_MS_T containing the time
 * \return integer
 * \retval 0 on success
 * \sa
 * \post
 * \pre
 * \bug  no known bugs
**/
int
msiGetFormattedSystemTime(msParam_t* outParam, msParam_t* inpParam,msParam_t* i
			   npFormatParam, ruleExecInfo_t *rei)
{
  char *format;
  char *dateFormat;
  char tStr0[TIME_LEN],tStr[TIME_LEN];
  int status;

  /* For testing mode when used with irule --test */
  RE_TEST_MACRO ("    Calling msiGetFormattedSystemTime");
    
    
  if (rei == NULL || rei->rsComm == NULL) {
    rodsLog (LOG_ERROR, 
	     "msiGetFormattedSystemTime: input rei or rsComm is NULL");
    return (SYS_INTERNAL_NULL_INPUT_ERR);
  }
  format = inpParam->inOutStruct;
  dateFormat = inpFormatParam->inOutStruct;
  
  if (!format || strcmp(format, "human")) {
    getNowStr(tStr);
  }
  else {
    getNowStr(tStr0);
    getFormattedLocalTimeFromRodsTime(tStr0,tStr,dateFormat);
  }
  status = fillStrInMsParam (outParam,tStr);
  return(status);
}

/**************************************************************************/
