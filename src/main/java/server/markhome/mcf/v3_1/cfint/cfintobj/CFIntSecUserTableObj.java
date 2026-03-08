// Description: Java 25 Table Object implementation for SecUser.

/*
 *	server.markhome.mcf.CFInt
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFInt - Internet Essentials
 *	
 *	This file is part of Mark's Code Fractal CFInt.
 *	
 *	Mark's Code Fractal CFInt is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFInt is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFInt is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFInt.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfint.cfintobj;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

public class CFIntSecUserTableObj
	implements ICFIntSecUserTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecSecUserObj> members;
	private Map<CFLibDbKeyHash256, ICFSecSecUserObj> allSecUser;
	private Map< ICFSecSecUserByULoginIdxKey,
		ICFSecSecUserObj > indexByULoginIdx;
	private Map< ICFSecSecUserByEMConfIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecUserObj > > indexByEMConfIdx;
	private Map< ICFSecSecUserByPwdResetIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecUserObj > > indexByPwdResetIdx;
	private Map< ICFSecSecUserByDefDevIdxKey,
		Map<CFLibDbKeyHash256, ICFSecSecUserObj > > indexByDefDevIdx;
	public static String TABLE_NAME = "SecUser";
	public static String TABLE_DBNAME = "secuser";

	public CFIntSecUserTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecUserObj>();
		allSecUser = null;
		indexByULoginIdx = null;
		indexByEMConfIdx = null;
		indexByPwdResetIdx = null;
		indexByDefDevIdx = null;
	}

	public CFIntSecUserTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecSecUserObj>();
		allSecUser = null;
		indexByULoginIdx = null;
		indexByEMConfIdx = null;
		indexByPwdResetIdx = null;
		indexByDefDevIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecSecUserTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecSecUserTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecSecUserTableObj.getRuntimeClassCode() );
	}

	@Override
	public ICFSecSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFSecSchemaObj value ) {
		schema = (ICFIntSchemaObj)value;
	}

	@Override
	public String getTableName() {
		return( TABLE_NAME );
	}

	@Override
	public String getTableDbName() {
		return( TABLE_DBNAME );
	}

	@Override
	public Class getObjQualifyingClass() {
		return( null );
	}


	@Override
	public void minimizeMemory() {
		allSecUser = null;
		indexByULoginIdx = null;
		indexByEMConfIdx = null;
		indexByPwdResetIdx = null;
		indexByDefDevIdx = null;
		List<ICFSecSecUserObj> toForget = new LinkedList<ICFSecSecUserObj>();
		ICFSecSecUserObj cur = null;
		Iterator<ICFSecSecUserObj> iter = members.values().iterator();
		while( iter.hasNext() ) {
			cur = iter.next();
			toForget.add( cur );
		}
		iter = toForget.iterator();
		while( iter.hasNext() ) {
			cur = iter.next();
			cur.forget();
		}
	}
	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSecUserObj.
	 */
	@Override
	public ICFSecSecUserObj newInstance() {
		ICFSecSecUserObj inst = new CFIntSecUserObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSecUserObj.
	 */
	@Override
	public ICFSecSecUserEditObj newEditInstance( ICFSecSecUserObj orig ) {
		ICFSecSecUserEditObj edit = new CFIntSecUserEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecSecUserObj realiseSecUser( ICFSecSecUserObj Obj ) {
		ICFSecSecUserObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecUserObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecSecUserObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByULoginIdx != null ) {
				ICFSecSecUserByULoginIdxKey keyULoginIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByULoginIdxKey();
				keyULoginIdx.setRequiredLoginId( keepObj.getRequiredLoginId() );
				indexByULoginIdx.remove( keyULoginIdx );
			}

			if( indexByEMConfIdx != null ) {
				ICFSecSecUserByEMConfIdxKey keyEMConfIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByEMConfIdxKey();
				keyEMConfIdx.setOptionalEMailConfirmUuid6( keepObj.getOptionalEMailConfirmUuid6() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapEMConfIdx = indexByEMConfIdx.get( keyEMConfIdx );
				if( mapEMConfIdx != null ) {
					mapEMConfIdx.remove( keepObj.getPKey() );
					if( mapEMConfIdx.size() <= 0 ) {
						indexByEMConfIdx.remove( keyEMConfIdx );
					}
				}
			}

			if( indexByPwdResetIdx != null ) {
				ICFSecSecUserByPwdResetIdxKey keyPwdResetIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByPwdResetIdxKey();
				keyPwdResetIdx.setOptionalPasswordResetUuid6( keepObj.getOptionalPasswordResetUuid6() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapPwdResetIdx = indexByPwdResetIdx.get( keyPwdResetIdx );
				if( mapPwdResetIdx != null ) {
					mapPwdResetIdx.remove( keepObj.getPKey() );
					if( mapPwdResetIdx.size() <= 0 ) {
						indexByPwdResetIdx.remove( keyPwdResetIdx );
					}
				}
			}

			if( indexByDefDevIdx != null ) {
				ICFSecSecUserByDefDevIdxKey keyDefDevIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByDefDevIdxKey();
				keyDefDevIdx.setOptionalDfltDevUserId( keepObj.getOptionalDfltDevUserId() );
				keyDefDevIdx.setOptionalDfltDevName( keepObj.getOptionalDfltDevName() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapDefDevIdx = indexByDefDevIdx.get( keyDefDevIdx );
				if( mapDefDevIdx != null ) {
					mapDefDevIdx.remove( keepObj.getPKey() );
					if( mapDefDevIdx.size() <= 0 ) {
						indexByDefDevIdx.remove( keyDefDevIdx );
					}
				}
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByULoginIdx != null ) {
				ICFSecSecUserByULoginIdxKey keyULoginIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByULoginIdxKey();
				keyULoginIdx.setRequiredLoginId( keepObj.getRequiredLoginId() );
				indexByULoginIdx.put( keyULoginIdx, keepObj );
			}

			if( indexByEMConfIdx != null ) {
				ICFSecSecUserByEMConfIdxKey keyEMConfIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByEMConfIdxKey();
				keyEMConfIdx.setOptionalEMailConfirmUuid6( keepObj.getOptionalEMailConfirmUuid6() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapEMConfIdx = indexByEMConfIdx.get( keyEMConfIdx );
				if( mapEMConfIdx != null ) {
					mapEMConfIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByPwdResetIdx != null ) {
				ICFSecSecUserByPwdResetIdxKey keyPwdResetIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByPwdResetIdxKey();
				keyPwdResetIdx.setOptionalPasswordResetUuid6( keepObj.getOptionalPasswordResetUuid6() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapPwdResetIdx = indexByPwdResetIdx.get( keyPwdResetIdx );
				if( mapPwdResetIdx != null ) {
					mapPwdResetIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByDefDevIdx != null ) {
				ICFSecSecUserByDefDevIdxKey keyDefDevIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByDefDevIdxKey();
				keyDefDevIdx.setOptionalDfltDevUserId( keepObj.getOptionalDfltDevUserId() );
				keyDefDevIdx.setOptionalDfltDevName( keepObj.getOptionalDfltDevName() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapDefDevIdx = indexByDefDevIdx.get( keyDefDevIdx );
				if( mapDefDevIdx != null ) {
					mapDefDevIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( allSecUser != null ) {
				allSecUser.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allSecUser != null ) {
				allSecUser.put( keepObj.getPKey(), keepObj );
			}

			if( indexByULoginIdx != null ) {
				ICFSecSecUserByULoginIdxKey keyULoginIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByULoginIdxKey();
				keyULoginIdx.setRequiredLoginId( keepObj.getRequiredLoginId() );
				indexByULoginIdx.put( keyULoginIdx, keepObj );
			}

			if( indexByEMConfIdx != null ) {
				ICFSecSecUserByEMConfIdxKey keyEMConfIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByEMConfIdxKey();
				keyEMConfIdx.setOptionalEMailConfirmUuid6( keepObj.getOptionalEMailConfirmUuid6() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapEMConfIdx = indexByEMConfIdx.get( keyEMConfIdx );
				if( mapEMConfIdx != null ) {
					mapEMConfIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByPwdResetIdx != null ) {
				ICFSecSecUserByPwdResetIdxKey keyPwdResetIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByPwdResetIdxKey();
				keyPwdResetIdx.setOptionalPasswordResetUuid6( keepObj.getOptionalPasswordResetUuid6() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapPwdResetIdx = indexByPwdResetIdx.get( keyPwdResetIdx );
				if( mapPwdResetIdx != null ) {
					mapPwdResetIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByDefDevIdx != null ) {
				ICFSecSecUserByDefDevIdxKey keyDefDevIdx =
					schema.getCFSecBackingStore().getFactorySecUser().newByDefDevIdxKey();
				keyDefDevIdx.setOptionalDfltDevUserId( keepObj.getOptionalDfltDevUserId() );
				keyDefDevIdx.setOptionalDfltDevName( keepObj.getOptionalDfltDevName() );
				Map<CFLibDbKeyHash256, ICFSecSecUserObj > mapDefDevIdx = indexByDefDevIdx.get( keyDefDevIdx );
				if( mapDefDevIdx != null ) {
					mapDefDevIdx.put( keepObj.getPKey(), keepObj );
				}
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecSecUserObj createSecUser( ICFSecSecUserObj Obj ) {
		ICFSecSecUserObj obj = Obj;
		ICFSecSecUser rec = obj.getSecUserRec();
		schema.getCFSecBackingStore().getTableSecUser().createSecUser(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecSecUserObj readSecUser( CFLibDbKeyHash256 pkey ) {
		return( readSecUser( pkey, false ) );
	}

	@Override
	public ICFSecSecUserObj readSecUser( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecSecUserObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecSecUser readRec = schema.getCFSecBackingStore().getTableSecUser().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getSecUserTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecSecUserObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecUserObj readCachedSecUser( CFLibDbKeyHash256 pkey ) {
		ICFSecSecUserObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeSecUser( ICFSecSecUserObj obj )
	{
		final String S_ProcName = "CFIntSecUserTableObj.reallyDeepDisposeSecUser() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecSecUserObj existing = readCachedSecUser( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecSecUserByULoginIdxKey keyULoginIdx = schema.getCFSecBackingStore().getFactorySecUser().newByULoginIdxKey();
		keyULoginIdx.setRequiredLoginId( existing.getRequiredLoginId() );

		ICFSecSecUserByEMConfIdxKey keyEMConfIdx = schema.getCFSecBackingStore().getFactorySecUser().newByEMConfIdxKey();
		keyEMConfIdx.setOptionalEMailConfirmUuid6( existing.getOptionalEMailConfirmUuid6() );

		ICFSecSecUserByPwdResetIdxKey keyPwdResetIdx = schema.getCFSecBackingStore().getFactorySecUser().newByPwdResetIdxKey();
		keyPwdResetIdx.setOptionalPasswordResetUuid6( existing.getOptionalPasswordResetUuid6() );

		ICFSecSecUserByDefDevIdxKey keyDefDevIdx = schema.getCFSecBackingStore().getFactorySecUser().newByDefDevIdxKey();
		keyDefDevIdx.setOptionalDfltDevUserId( existing.getOptionalDfltDevUserId() );
		keyDefDevIdx.setOptionalDfltDevName( existing.getOptionalDfltDevName() );


					schema.getTSecGrpMembTableObj().deepDisposeTSecGrpMembByUserIdx( existing.getRequiredSecUserId() );
					schema.getSecGrpMembTableObj().deepDisposeSecGrpMembByUserIdx( existing.getRequiredSecUserId() );
					schema.getSecDeviceTableObj().deepDisposeSecDeviceByUserIdx( existing.getRequiredSecUserId() );

		if( indexByULoginIdx != null ) {
			indexByULoginIdx.remove( keyULoginIdx );
		}

		if( indexByEMConfIdx != null ) {
			if( indexByEMConfIdx.containsKey( keyEMConfIdx ) ) {
				indexByEMConfIdx.get( keyEMConfIdx ).remove( pkey );
				if( indexByEMConfIdx.get( keyEMConfIdx ).size() <= 0 ) {
					indexByEMConfIdx.remove( keyEMConfIdx );
				}
			}
		}

		if( indexByPwdResetIdx != null ) {
			if( indexByPwdResetIdx.containsKey( keyPwdResetIdx ) ) {
				indexByPwdResetIdx.get( keyPwdResetIdx ).remove( pkey );
				if( indexByPwdResetIdx.get( keyPwdResetIdx ).size() <= 0 ) {
					indexByPwdResetIdx.remove( keyPwdResetIdx );
				}
			}
		}

		if( indexByDefDevIdx != null ) {
			if( indexByDefDevIdx.containsKey( keyDefDevIdx ) ) {
				indexByDefDevIdx.get( keyDefDevIdx ).remove( pkey );
				if( indexByDefDevIdx.get( keyDefDevIdx ).size() <= 0 ) {
					indexByDefDevIdx.remove( keyDefDevIdx );
				}
			}
		}


	}
	@Override
	public void deepDisposeSecUser( CFLibDbKeyHash256 pkey ) {
		ICFSecSecUserObj obj = readCachedSecUser( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecSecUserObj lockSecUser( CFLibDbKeyHash256 pkey ) {
		ICFSecSecUserObj locked = null;
		ICFSecSecUser lockRec = schema.getCFSecBackingStore().getTableSecUser().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getSecUserTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecSecUserObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockSecUser", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecSecUserObj> readAllSecUser() {
		return( readAllSecUser( false ) );
	}

	@Override
	public List<ICFSecSecUserObj> readAllSecUser( boolean forceRead ) {
		final String S_ProcName = "readAllSecUser";
		if( ( allSecUser == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecSecUserObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecUserObj>();
			allSecUser = map;
			ICFSecSecUser[] recList = schema.getCFSecBackingStore().getTableSecUser().readAllDerived( null );
			ICFSecSecUser rec;
			ICFSecSecUserObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecUserObj realised = (ICFSecSecUserObj)obj.realise();
			}
		}
		int len = allSecUser.size();
		ICFSecSecUserObj arr[] = new ICFSecSecUserObj[len];
		Iterator<ICFSecSecUserObj> valIter = allSecUser.values().iterator();
		int idx = 0;
		while( ( idx < len ) && valIter.hasNext() ) {
			arr[idx++] = valIter.next();
		}
		if( idx < len ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"idx",
				idx,
				len );
		}
		else if( valIter.hasNext() ) {
			throw new CFLibArgumentOverflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
		}
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecUserObj> cmp = new Comparator<ICFSecSecUserObj>() {
			@Override
			public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecSecUserObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecUserObj> readCachedAllSecUser() {
		final String S_ProcName = "readCachedAllSecUser";
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>();
		if( allSecUser != null ) {
			int len = allSecUser.size();
			ICFSecSecUserObj arr[] = new ICFSecSecUserObj[len];
			Iterator<ICFSecSecUserObj> valIter = allSecUser.values().iterator();
			int idx = 0;
			while( ( idx < len ) && valIter.hasNext() ) {
				arr[idx++] = valIter.next();
			}
			if( idx < len ) {
				throw new CFLibArgumentUnderflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
			}
			else if( valIter.hasNext() ) {
				throw new CFLibArgumentOverflowException( getClass(),
						S_ProcName,
						0,
						"idx",
						idx,
						len );
			}
			for( idx = 0; idx < len; idx ++ ) {
				arrayList.add( arr[idx] );
			}
		}
		Comparator<ICFSecSecUserObj> cmp = new Comparator<ICFSecSecUserObj>() {
			public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	/**
	 *	Return a sorted map of a page of the SecUser-derived instances in the database.
	 *
	 *	@return	List of ICFSecSecUserObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecSecUserObj> pageAllSecUser(CFLibDbKeyHash256 priorSecUserId )
	{
		final String S_ProcName = "pageAllSecUser";
		Map<CFLibDbKeyHash256, ICFSecSecUserObj> map = new HashMap<CFLibDbKeyHash256,ICFSecSecUserObj>();
		ICFSecSecUser[] recList = schema.getCFSecBackingStore().getTableSecUser().pageAllRec( null,
			priorSecUserId );
		ICFSecSecUser rec;
		ICFSecSecUserObj obj;
		ICFSecSecUserObj realised;
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecSecUserObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecSecUserObj readSecUserByIdIdx( CFLibDbKeyHash256 SecUserId )
	{
		return( readSecUserByIdIdx( SecUserId,
			false ) );
	}

	@Override
	public ICFSecSecUserObj readSecUserByIdIdx( CFLibDbKeyHash256 SecUserId, boolean forceRead )
	{
		ICFSecSecUserObj obj = readSecUser( SecUserId, forceRead );
		return( obj );
	}

	@Override
	public ICFSecSecUserObj readSecUserByULoginIdx( String LoginId )
	{
		return( readSecUserByULoginIdx( LoginId,
			false ) );
	}

	@Override
	public ICFSecSecUserObj readSecUserByULoginIdx( String LoginId, boolean forceRead )
	{
		if( indexByULoginIdx == null ) {
			indexByULoginIdx = new HashMap< ICFSecSecUserByULoginIdxKey,
				ICFSecSecUserObj >();
		}
		ICFSecSecUserByULoginIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByULoginIdxKey();
		key.setRequiredLoginId( LoginId );
		ICFSecSecUserObj obj = null;
		if( ( ! forceRead ) && indexByULoginIdx.containsKey( key ) ) {
			obj = indexByULoginIdx.get( key );
		}
		else {
			ICFSecSecUser rec = schema.getCFSecBackingStore().getTableSecUser().readDerivedByULoginIdx( null,
				LoginId );
			if( rec != null ) {
				obj = schema.getSecUserTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecSecUserObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public List<ICFSecSecUserObj> readSecUserByEMConfIdx( CFLibUuid6 EMailConfirmUuid6 )
	{
		return( readSecUserByEMConfIdx( EMailConfirmUuid6,
			false ) );
	}

	@Override
	public List<ICFSecSecUserObj> readSecUserByEMConfIdx( CFLibUuid6 EMailConfirmUuid6,
		boolean forceRead )
	{
		final String S_ProcName = "readSecUserByEMConfIdx";
		ICFSecSecUserByEMConfIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByEMConfIdxKey();
		key.setOptionalEMailConfirmUuid6( EMailConfirmUuid6 );
		Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict;
		if( indexByEMConfIdx == null ) {
			indexByEMConfIdx = new HashMap< ICFSecSecUserByEMConfIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecUserObj > >();
		}
		if( ( ! forceRead ) && indexByEMConfIdx.containsKey( key ) ) {
			dict = indexByEMConfIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecUserObj>();
			ICFSecSecUserObj obj;
			ICFSecSecUser[] recList = schema.getCFSecBackingStore().getTableSecUser().readDerivedByEMConfIdx( null,
				EMailConfirmUuid6 );
			ICFSecSecUser rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecUserTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecUserObj realised = (ICFSecSecUserObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByEMConfIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecUserObj arr[] = new ICFSecSecUserObj[len];
		Iterator<ICFSecSecUserObj> valIter = dict.values().iterator();
		int idx = 0;
		while( ( idx < len ) && valIter.hasNext() ) {
			arr[idx++] = valIter.next();
		}
		if( idx < len ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"idx",
				idx,
				len );
		}
		else if( valIter.hasNext() ) {
			throw new CFLibArgumentOverflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
		}
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecUserObj> cmp = new Comparator<ICFSecSecUserObj>() {
			@Override
			public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecSecUserObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecUserObj> readSecUserByPwdResetIdx( CFLibUuid6 PasswordResetUuid6 )
	{
		return( readSecUserByPwdResetIdx( PasswordResetUuid6,
			false ) );
	}

	@Override
	public List<ICFSecSecUserObj> readSecUserByPwdResetIdx( CFLibUuid6 PasswordResetUuid6,
		boolean forceRead )
	{
		final String S_ProcName = "readSecUserByPwdResetIdx";
		ICFSecSecUserByPwdResetIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByPwdResetIdxKey();
		key.setOptionalPasswordResetUuid6( PasswordResetUuid6 );
		Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict;
		if( indexByPwdResetIdx == null ) {
			indexByPwdResetIdx = new HashMap< ICFSecSecUserByPwdResetIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecUserObj > >();
		}
		if( ( ! forceRead ) && indexByPwdResetIdx.containsKey( key ) ) {
			dict = indexByPwdResetIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecUserObj>();
			ICFSecSecUserObj obj;
			ICFSecSecUser[] recList = schema.getCFSecBackingStore().getTableSecUser().readDerivedByPwdResetIdx( null,
				PasswordResetUuid6 );
			ICFSecSecUser rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecUserTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecUserObj realised = (ICFSecSecUserObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByPwdResetIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecUserObj arr[] = new ICFSecSecUserObj[len];
		Iterator<ICFSecSecUserObj> valIter = dict.values().iterator();
		int idx = 0;
		while( ( idx < len ) && valIter.hasNext() ) {
			arr[idx++] = valIter.next();
		}
		if( idx < len ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"idx",
				idx,
				len );
		}
		else if( valIter.hasNext() ) {
			throw new CFLibArgumentOverflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
		}
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecUserObj> cmp = new Comparator<ICFSecSecUserObj>() {
			@Override
			public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecSecUserObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecUserObj> readSecUserByDefDevIdx( CFLibDbKeyHash256 DfltDevUserId,
		String DfltDevName )
	{
		return( readSecUserByDefDevIdx( DfltDevUserId,
			DfltDevName,
			false ) );
	}

	@Override
	public List<ICFSecSecUserObj> readSecUserByDefDevIdx( CFLibDbKeyHash256 DfltDevUserId,
		String DfltDevName,
		boolean forceRead )
	{
		final String S_ProcName = "readSecUserByDefDevIdx";
		ICFSecSecUserByDefDevIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByDefDevIdxKey();
		key.setOptionalDfltDevUserId( DfltDevUserId );
		key.setOptionalDfltDevName( DfltDevName );
		Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict;
		if( indexByDefDevIdx == null ) {
			indexByDefDevIdx = new HashMap< ICFSecSecUserByDefDevIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecUserObj > >();
		}
		if( ( ! forceRead ) && indexByDefDevIdx.containsKey( key ) ) {
			dict = indexByDefDevIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecSecUserObj>();
			ICFSecSecUserObj obj;
			ICFSecSecUser[] recList = schema.getCFSecBackingStore().getTableSecUser().readDerivedByDefDevIdx( null,
				DfltDevUserId,
				DfltDevName );
			ICFSecSecUser rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecUserTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecUserObj realised = (ICFSecSecUserObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByDefDevIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecUserObj arr[] = new ICFSecSecUserObj[len];
		Iterator<ICFSecSecUserObj> valIter = dict.values().iterator();
		int idx = 0;
		while( ( idx < len ) && valIter.hasNext() ) {
			arr[idx++] = valIter.next();
		}
		if( idx < len ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"idx",
				idx,
				len );
		}
		else if( valIter.hasNext() ) {
			throw new CFLibArgumentOverflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
		}
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecUserObj> cmp = new Comparator<ICFSecSecUserObj>() {
			@Override
			public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecSecUserObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecSecUserObj readCachedSecUserByIdIdx( CFLibDbKeyHash256 SecUserId )
	{
		ICFSecSecUserObj obj = null;
		obj = readCachedSecUser( SecUserId );
		return( obj );
	}

	@Override
	public ICFSecSecUserObj readCachedSecUserByULoginIdx( String LoginId )
	{
		ICFSecSecUserObj obj = null;
		ICFSecSecUserByULoginIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByULoginIdxKey();
		key.setRequiredLoginId( LoginId );
		if( indexByULoginIdx != null ) {
			if( indexByULoginIdx.containsKey( key ) ) {
				obj = indexByULoginIdx.get( key );
			}
			else {
				Iterator<ICFSecSecUserObj> valIter = members.values().iterator();
				while( ( obj == null ) && valIter.hasNext() ) {
					obj = valIter.next();
					if( obj != null ) {
						if( obj.getRec().compareTo( key ) != 0 ) {
							obj = null;
						}
					}
				}
			}
		}
		else {
			Iterator<ICFSecSecUserObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) != 0 ) {
						obj = null;
					}
				}
			}
		}
		return( obj );
	}

	@Override
	public List<ICFSecSecUserObj> readCachedSecUserByEMConfIdx( CFLibUuid6 EMailConfirmUuid6 )
	{
		final String S_ProcName = "readCachedSecUserByEMConfIdx";
		ICFSecSecUserByEMConfIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByEMConfIdxKey();
		key.setOptionalEMailConfirmUuid6( EMailConfirmUuid6 );
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>();
		if( indexByEMConfIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict;
			if( indexByEMConfIdx.containsKey( key ) ) {
				dict = indexByEMConfIdx.get( key );
				int len = dict.size();
				ICFSecSecUserObj arr[] = new ICFSecSecUserObj[len];
				Iterator<ICFSecSecUserObj> valIter = dict.values().iterator();
				int idx = 0;
				while( ( idx < len ) && valIter.hasNext() ) {
					arr[idx++] = valIter.next();
				}
				if( idx < len ) {
					throw new CFLibArgumentUnderflowException( getClass(),
						S_ProcName,
						0,
						"idx",
						idx,
						len );
				}
				else if( valIter.hasNext() ) {
					throw new CFLibArgumentOverflowException( getClass(),
							S_ProcName,
							0,
							"idx",
							idx,
							len );
				}
				for( idx = 0; idx < len; idx ++ ) {
					arrayList.add( arr[idx] );
				}
			}
		}
		else {
			ICFSecSecUserObj obj;
			Iterator<ICFSecSecUserObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecUserObj> cmp = new Comparator<ICFSecSecUserObj>() {
			@Override
			public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public List<ICFSecSecUserObj> readCachedSecUserByPwdResetIdx( CFLibUuid6 PasswordResetUuid6 )
	{
		final String S_ProcName = "readCachedSecUserByPwdResetIdx";
		ICFSecSecUserByPwdResetIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByPwdResetIdxKey();
		key.setOptionalPasswordResetUuid6( PasswordResetUuid6 );
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>();
		if( indexByPwdResetIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict;
			if( indexByPwdResetIdx.containsKey( key ) ) {
				dict = indexByPwdResetIdx.get( key );
				int len = dict.size();
				ICFSecSecUserObj arr[] = new ICFSecSecUserObj[len];
				Iterator<ICFSecSecUserObj> valIter = dict.values().iterator();
				int idx = 0;
				while( ( idx < len ) && valIter.hasNext() ) {
					arr[idx++] = valIter.next();
				}
				if( idx < len ) {
					throw new CFLibArgumentUnderflowException( getClass(),
						S_ProcName,
						0,
						"idx",
						idx,
						len );
				}
				else if( valIter.hasNext() ) {
					throw new CFLibArgumentOverflowException( getClass(),
							S_ProcName,
							0,
							"idx",
							idx,
							len );
				}
				for( idx = 0; idx < len; idx ++ ) {
					arrayList.add( arr[idx] );
				}
			}
		}
		else {
			ICFSecSecUserObj obj;
			Iterator<ICFSecSecUserObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecUserObj> cmp = new Comparator<ICFSecSecUserObj>() {
			@Override
			public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public List<ICFSecSecUserObj> readCachedSecUserByDefDevIdx( CFLibDbKeyHash256 DfltDevUserId,
		String DfltDevName )
	{
		final String S_ProcName = "readCachedSecUserByDefDevIdx";
		ICFSecSecUserByDefDevIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByDefDevIdxKey();
		key.setOptionalDfltDevUserId( DfltDevUserId );
		key.setOptionalDfltDevName( DfltDevName );
		ArrayList<ICFSecSecUserObj> arrayList = new ArrayList<ICFSecSecUserObj>();
		if( indexByDefDevIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict;
			if( indexByDefDevIdx.containsKey( key ) ) {
				dict = indexByDefDevIdx.get( key );
				int len = dict.size();
				ICFSecSecUserObj arr[] = new ICFSecSecUserObj[len];
				Iterator<ICFSecSecUserObj> valIter = dict.values().iterator();
				int idx = 0;
				while( ( idx < len ) && valIter.hasNext() ) {
					arr[idx++] = valIter.next();
				}
				if( idx < len ) {
					throw new CFLibArgumentUnderflowException( getClass(),
						S_ProcName,
						0,
						"idx",
						idx,
						len );
				}
				else if( valIter.hasNext() ) {
					throw new CFLibArgumentOverflowException( getClass(),
							S_ProcName,
							0,
							"idx",
							idx,
							len );
				}
				for( idx = 0; idx < len; idx ++ ) {
					arrayList.add( arr[idx] );
				}
			}
		}
		else {
			ICFSecSecUserObj obj;
			Iterator<ICFSecSecUserObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecUserObj> cmp = new Comparator<ICFSecSecUserObj>() {
			@Override
			public int compare( ICFSecSecUserObj lhs, ICFSecSecUserObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public void deepDisposeSecUserByIdIdx( CFLibDbKeyHash256 SecUserId )
	{
		ICFSecSecUserObj obj = readCachedSecUserByIdIdx( SecUserId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecUserByULoginIdx( String LoginId )
	{
		ICFSecSecUserObj obj = readCachedSecUserByULoginIdx( LoginId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecUserByEMConfIdx( CFLibUuid6 EMailConfirmUuid6 )
	{
		final String S_ProcName = "deepDisposeSecUserByEMConfIdx";
		ICFSecSecUserObj obj;
		List<ICFSecSecUserObj> arrayList = readCachedSecUserByEMConfIdx( EMailConfirmUuid6 );
		if( arrayList != null )  {
			Iterator<ICFSecSecUserObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecUserByPwdResetIdx( CFLibUuid6 PasswordResetUuid6 )
	{
		final String S_ProcName = "deepDisposeSecUserByPwdResetIdx";
		ICFSecSecUserObj obj;
		List<ICFSecSecUserObj> arrayList = readCachedSecUserByPwdResetIdx( PasswordResetUuid6 );
		if( arrayList != null )  {
			Iterator<ICFSecSecUserObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeSecUserByDefDevIdx( CFLibDbKeyHash256 DfltDevUserId,
		String DfltDevName )
	{
		final String S_ProcName = "deepDisposeSecUserByDefDevIdx";
		ICFSecSecUserObj obj;
		List<ICFSecSecUserObj> arrayList = readCachedSecUserByDefDevIdx( DfltDevUserId,
				DfltDevName );
		if( arrayList != null )  {
			Iterator<ICFSecSecUserObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	/**
	 *	Read a page of data as a List of SecUser-derived instances sorted by their primary keys,
	 *	as identified by the duplicate EMConfIdx key attributes.
	 *
	 *	@param	EMailConfirmUuid6	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecUser-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecUserObj> pageSecUserByEMConfIdx( CFLibUuid6 EMailConfirmUuid6,
		CFLibDbKeyHash256 priorSecUserId )
	{
		final String S_ProcName = "pageSecUserByEMConfIdx";
		ICFSecSecUserByEMConfIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByEMConfIdxKey();
		key.setOptionalEMailConfirmUuid6( EMailConfirmUuid6 );
		List<ICFSecSecUserObj> retList = new LinkedList<ICFSecSecUserObj>();
		ICFSecSecUserObj obj;
		ICFSecSecUser[] recList = schema.getCFSecBackingStore().getTableSecUser().pageRecByEMConfIdx( null,
				EMailConfirmUuid6,
			priorSecUserId );
		ICFSecSecUser rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecUserTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecUserObj realised = (ICFSecSecUserObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecUser-derived instances sorted by their primary keys,
	 *	as identified by the duplicate PwdResetIdx key attributes.
	 *
	 *	@param	PasswordResetUuid6	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecUser-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecUserObj> pageSecUserByPwdResetIdx( CFLibUuid6 PasswordResetUuid6,
		CFLibDbKeyHash256 priorSecUserId )
	{
		final String S_ProcName = "pageSecUserByPwdResetIdx";
		ICFSecSecUserByPwdResetIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByPwdResetIdxKey();
		key.setOptionalPasswordResetUuid6( PasswordResetUuid6 );
		List<ICFSecSecUserObj> retList = new LinkedList<ICFSecSecUserObj>();
		ICFSecSecUserObj obj;
		ICFSecSecUser[] recList = schema.getCFSecBackingStore().getTableSecUser().pageRecByPwdResetIdx( null,
				PasswordResetUuid6,
			priorSecUserId );
		ICFSecSecUser rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecUserTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecUserObj realised = (ICFSecSecUserObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of SecUser-derived instances sorted by their primary keys,
	 *	as identified by the duplicate DefDevIdx key attributes.
	 *
	 *	@param	DfltDevUserId	The SecUser key attribute of the instance generating the id.
	 *
	 *	@param	DfltDevName	The SecUser key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecUser-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecUserObj> pageSecUserByDefDevIdx( CFLibDbKeyHash256 DfltDevUserId,
		String DfltDevName,
		CFLibDbKeyHash256 priorSecUserId )
	{
		final String S_ProcName = "pageSecUserByDefDevIdx";
		ICFSecSecUserByDefDevIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByDefDevIdxKey();
		key.setOptionalDfltDevUserId( DfltDevUserId );
		key.setOptionalDfltDevName( DfltDevName );
		List<ICFSecSecUserObj> retList = new LinkedList<ICFSecSecUserObj>();
		ICFSecSecUserObj obj;
		ICFSecSecUser[] recList = schema.getCFSecBackingStore().getTableSecUser().pageRecByDefDevIdx( null,
				DfltDevUserId,
				DfltDevName,
			priorSecUserId );
		ICFSecSecUser rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecUserTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecUserObj realised = (ICFSecSecUserObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecSecUserObj updateSecUser( ICFSecSecUserObj Obj ) {
		ICFSecSecUserObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecUser().updateSecUser( null,
			Obj.getSecUserRec() );
		obj = (ICFSecSecUserObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteSecUser( ICFSecSecUserObj Obj ) {
		ICFSecSecUserObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecUser().deleteSecUser( null,
			obj.getSecUserRec() );
		Obj.forget();
	}

	@Override
	public void deleteSecUserByIdIdx( CFLibDbKeyHash256 SecUserId )
	{
		ICFSecSecUserObj obj = readSecUser(SecUserId);
		if( obj != null ) {
			ICFSecSecUserEditObj editObj = (ICFSecSecUserEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecSecUserEditObj)obj.beginEdit();
				if( editObj != null ) {
					editStarted = true;
				}
				else {
					editStarted = false;
				}
			}
			else {
				editStarted = false;
			}
			if( editObj != null ) {
				editObj.deleteInstance();
				if( editStarted ) {
					editObj.endEdit();
				}
			}
			obj.forget();
		}
		deepDisposeSecUserByIdIdx( SecUserId );
	}

	@Override
	public void deleteSecUserByULoginIdx( String LoginId )
	{
		if( indexByULoginIdx == null ) {
			indexByULoginIdx = new HashMap< ICFSecSecUserByULoginIdxKey,
				ICFSecSecUserObj >();
		}
		ICFSecSecUserByULoginIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByULoginIdxKey();
		key.setRequiredLoginId( LoginId );
		ICFSecSecUserObj obj = null;
		if( indexByULoginIdx.containsKey( key ) ) {
			obj = indexByULoginIdx.get( key );
			schema.getCFSecBackingStore().getTableSecUser().deleteSecUserByULoginIdx( null,
				LoginId );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableSecUser().deleteSecUserByULoginIdx( null,
				LoginId );
		}
		deepDisposeSecUserByULoginIdx( LoginId );
	}

	@Override
	public void deleteSecUserByEMConfIdx( CFLibUuid6 EMailConfirmUuid6 )
	{
		ICFSecSecUserByEMConfIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByEMConfIdxKey();
		key.setOptionalEMailConfirmUuid6( EMailConfirmUuid6 );
		if( indexByEMConfIdx == null ) {
			indexByEMConfIdx = new HashMap< ICFSecSecUserByEMConfIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecUserObj > >();
		}
		if( indexByEMConfIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict = indexByEMConfIdx.get( key );
			schema.getCFSecBackingStore().getTableSecUser().deleteSecUserByEMConfIdx( null,
				EMailConfirmUuid6 );
			Iterator<ICFSecSecUserObj> iter = dict.values().iterator();
			ICFSecSecUserObj obj;
			List<ICFSecSecUserObj> toForget = new LinkedList<ICFSecSecUserObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByEMConfIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecUser().deleteSecUserByEMConfIdx( null,
				EMailConfirmUuid6 );
		}
		deepDisposeSecUserByEMConfIdx( EMailConfirmUuid6 );
	}

	@Override
	public void deleteSecUserByPwdResetIdx( CFLibUuid6 PasswordResetUuid6 )
	{
		ICFSecSecUserByPwdResetIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByPwdResetIdxKey();
		key.setOptionalPasswordResetUuid6( PasswordResetUuid6 );
		if( indexByPwdResetIdx == null ) {
			indexByPwdResetIdx = new HashMap< ICFSecSecUserByPwdResetIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecUserObj > >();
		}
		if( indexByPwdResetIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict = indexByPwdResetIdx.get( key );
			schema.getCFSecBackingStore().getTableSecUser().deleteSecUserByPwdResetIdx( null,
				PasswordResetUuid6 );
			Iterator<ICFSecSecUserObj> iter = dict.values().iterator();
			ICFSecSecUserObj obj;
			List<ICFSecSecUserObj> toForget = new LinkedList<ICFSecSecUserObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByPwdResetIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecUser().deleteSecUserByPwdResetIdx( null,
				PasswordResetUuid6 );
		}
		deepDisposeSecUserByPwdResetIdx( PasswordResetUuid6 );
	}

	@Override
	public void deleteSecUserByDefDevIdx( CFLibDbKeyHash256 DfltDevUserId,
		String DfltDevName )
	{
		ICFSecSecUserByDefDevIdxKey key = schema.getCFSecBackingStore().getFactorySecUser().newByDefDevIdxKey();
		key.setOptionalDfltDevUserId( DfltDevUserId );
		key.setOptionalDfltDevName( DfltDevName );
		if( indexByDefDevIdx == null ) {
			indexByDefDevIdx = new HashMap< ICFSecSecUserByDefDevIdxKey,
				Map< CFLibDbKeyHash256, ICFSecSecUserObj > >();
		}
		if( indexByDefDevIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecSecUserObj> dict = indexByDefDevIdx.get( key );
			schema.getCFSecBackingStore().getTableSecUser().deleteSecUserByDefDevIdx( null,
				DfltDevUserId,
				DfltDevName );
			Iterator<ICFSecSecUserObj> iter = dict.values().iterator();
			ICFSecSecUserObj obj;
			List<ICFSecSecUserObj> toForget = new LinkedList<ICFSecSecUserObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByDefDevIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecUser().deleteSecUserByDefDevIdx( null,
				DfltDevUserId,
				DfltDevName );
		}
		deepDisposeSecUserByDefDevIdx( DfltDevUserId,
				DfltDevName );
	}

	public ICFSecSecUserObj getSystemUser() {
		ICFSecSecUserObj secUserObj;
		secUserObj = schema.getSecUserTableObj().readSecUserByULoginIdx( "system" );
		if( secUserObj == null ) {
			secUserObj = newInstance();
			ICFSecSecUserEditObj secUserEdit = secUserObj.beginEdit();
			secUserEdit.setRequiredEMailAddress( "system" );
			secUserObj = secUserEdit.create();
			secUserEdit = null;
		}
		return( secUserObj );
	}
}