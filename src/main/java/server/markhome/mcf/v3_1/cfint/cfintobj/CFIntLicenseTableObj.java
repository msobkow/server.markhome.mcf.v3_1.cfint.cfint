// Description: Java 25 Table Object implementation for License.

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
import server.markhome.mcf.v3_1.cfint.cfint.*;

public class CFIntLicenseTableObj
	implements ICFIntLicenseTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntLicense.CLASS_CODE;
	protected static final int backingClassCode = ICFIntLicense.CLASS_CODE;
	private Map<CFLibDbKeyHash256, ICFIntLicenseObj> members;
	private Map<CFLibDbKeyHash256, ICFIntLicenseObj> allLicense;
	private Map< ICFIntLicenseByLicnTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFIntLicenseObj > > indexByLicnTenantIdx;
	private Map< ICFIntLicenseByDomainIdxKey,
		Map<CFLibDbKeyHash256, ICFIntLicenseObj > > indexByDomainIdx;
	private Map< ICFIntLicenseByUNameIdxKey,
		ICFIntLicenseObj > indexByUNameIdx;
	public static String TABLE_NAME = "License";
	public static String TABLE_DBNAME = "licn";

	public CFIntLicenseTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFIntLicenseObj>();
		allLicense = null;
		indexByLicnTenantIdx = null;
		indexByDomainIdx = null;
		indexByUNameIdx = null;
	}

	public CFIntLicenseTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFIntLicenseObj>();
		allLicense = null;
		indexByLicnTenantIdx = null;
		indexByDomainIdx = null;
		indexByUNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntLicenseTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( backingClassCode );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( runtimeClassCode );
	}

	/**
	 *	Set the runtime class code for this table; this is done only during application initialization by the SchemaObj's <tt>initClassCodes()</tt> static method,
	 *	which will only set the class codes once and never again.  Once set, the class codes are immutable within the application.
	 *	Application programmers should never invoke this method, so it has package access only.
	 *
	 *	@param	argNewClassCode	The runtime class code to be used by clients and integrated application logic to identify this table of this schema.
	 */
	static void setRuntimeClassCode(int argNewClassCode ) {
		if (argNewClassCode <= 0) {
			throw new CFLibArgumentUnderflowException(CFIntLicenseTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
		}
		runtimeClassCode = argNewClassCode;
	}

	@Override
	public ICFIntSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFIntSchemaObj value ) {
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
		return( ICFIntTenantObj.class );
	}


	@Override
	public void minimizeMemory() {
		allLicense = null;
		indexByLicnTenantIdx = null;
		indexByDomainIdx = null;
		indexByUNameIdx = null;
		List<ICFIntLicenseObj> toForget = new LinkedList<ICFIntLicenseObj>();
		ICFIntLicenseObj cur = null;
		Iterator<ICFIntLicenseObj> iter = members.values().iterator();
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
	 *	CFIntLicenseObj.
	 */
	@Override
	public ICFIntLicenseObj newInstance() {
		ICFIntLicenseObj inst = new CFIntLicenseObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntLicenseObj.
	 */
	@Override
	public ICFIntLicenseEditObj newEditInstance( ICFIntLicenseObj orig ) {
		ICFIntLicenseEditObj edit = new CFIntLicenseEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntLicenseObj realiseLicense( ICFIntLicenseObj Obj ) {
		ICFIntLicenseObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntLicenseObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntLicenseObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByLicnTenantIdx != null ) {
				ICFIntLicenseByLicnTenantIdxKey keyLicnTenantIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByLicnTenantIdxKey();
				keyLicnTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntLicenseObj > mapLicnTenantIdx = indexByLicnTenantIdx.get( keyLicnTenantIdx );
				if( mapLicnTenantIdx != null ) {
					mapLicnTenantIdx.remove( keepObj.getPKey() );
					if( mapLicnTenantIdx.size() <= 0 ) {
						indexByLicnTenantIdx.remove( keyLicnTenantIdx );
					}
				}
			}

			if( indexByDomainIdx != null ) {
				ICFIntLicenseByDomainIdxKey keyDomainIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByDomainIdxKey();
				keyDomainIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				Map<CFLibDbKeyHash256, ICFIntLicenseObj > mapDomainIdx = indexByDomainIdx.get( keyDomainIdx );
				if( mapDomainIdx != null ) {
					mapDomainIdx.remove( keepObj.getPKey() );
					if( mapDomainIdx.size() <= 0 ) {
						indexByDomainIdx.remove( keyDomainIdx );
					}
				}
			}

			if( indexByUNameIdx != null ) {
				ICFIntLicenseByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByUNameIdxKey();
				keyUNameIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.remove( keyUNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByLicnTenantIdx != null ) {
				ICFIntLicenseByLicnTenantIdxKey keyLicnTenantIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByLicnTenantIdxKey();
				keyLicnTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntLicenseObj > mapLicnTenantIdx = indexByLicnTenantIdx.get( keyLicnTenantIdx );
				if( mapLicnTenantIdx != null ) {
					mapLicnTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByDomainIdx != null ) {
				ICFIntLicenseByDomainIdxKey keyDomainIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByDomainIdxKey();
				keyDomainIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				Map<CFLibDbKeyHash256, ICFIntLicenseObj > mapDomainIdx = indexByDomainIdx.get( keyDomainIdx );
				if( mapDomainIdx != null ) {
					mapDomainIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUNameIdx != null ) {
				ICFIntLicenseByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByUNameIdxKey();
				keyUNameIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

			if( allLicense != null ) {
				allLicense.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allLicense != null ) {
				allLicense.put( keepObj.getPKey(), keepObj );
			}

			if( indexByLicnTenantIdx != null ) {
				ICFIntLicenseByLicnTenantIdxKey keyLicnTenantIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByLicnTenantIdxKey();
				keyLicnTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntLicenseObj > mapLicnTenantIdx = indexByLicnTenantIdx.get( keyLicnTenantIdx );
				if( mapLicnTenantIdx != null ) {
					mapLicnTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByDomainIdx != null ) {
				ICFIntLicenseByDomainIdxKey keyDomainIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByDomainIdxKey();
				keyDomainIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				Map<CFLibDbKeyHash256, ICFIntLicenseObj > mapDomainIdx = indexByDomainIdx.get( keyDomainIdx );
				if( mapDomainIdx != null ) {
					mapDomainIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUNameIdx != null ) {
				ICFIntLicenseByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryLicense().newByUNameIdxKey();
				keyUNameIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntLicenseObj createLicense( ICFIntLicenseObj Obj ) {
		ICFIntLicenseObj obj = Obj;
		ICFIntLicense rec = obj.getLicenseRec();
		schema.getCFIntBackingStore().getTableLicense().createLicense(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntLicenseObj readLicense( CFLibDbKeyHash256 pkey ) {
		return( readLicense( pkey, false ) );
	}

	@Override
	public ICFIntLicenseObj readLicense( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFIntLicenseObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntLicense readRec = schema.getCFIntBackingStore().getTableLicense().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getLicenseTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntLicenseObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntLicenseObj readCachedLicense( CFLibDbKeyHash256 pkey ) {
		ICFIntLicenseObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeLicense( ICFIntLicenseObj obj )
	{
		final String S_ProcName = "CFIntLicenseTableObj.reallyDeepDisposeLicense() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntLicenseObj existing = readCachedLicense( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntLicenseByLicnTenantIdxKey keyLicnTenantIdx = schema.getCFIntBackingStore().getFactoryLicense().newByLicnTenantIdxKey();
		keyLicnTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFIntLicenseByDomainIdxKey keyDomainIdx = schema.getCFIntBackingStore().getFactoryLicense().newByDomainIdxKey();
		keyDomainIdx.setRequiredTopDomainId( existing.getRequiredTopDomainId() );

		ICFIntLicenseByUNameIdxKey keyUNameIdx = schema.getCFIntBackingStore().getFactoryLicense().newByUNameIdxKey();
		keyUNameIdx.setRequiredTopDomainId( existing.getRequiredTopDomainId() );
		keyUNameIdx.setRequiredName( existing.getRequiredName() );



		if( indexByLicnTenantIdx != null ) {
			if( indexByLicnTenantIdx.containsKey( keyLicnTenantIdx ) ) {
				indexByLicnTenantIdx.get( keyLicnTenantIdx ).remove( pkey );
				if( indexByLicnTenantIdx.get( keyLicnTenantIdx ).size() <= 0 ) {
					indexByLicnTenantIdx.remove( keyLicnTenantIdx );
				}
			}
		}

		if( indexByDomainIdx != null ) {
			if( indexByDomainIdx.containsKey( keyDomainIdx ) ) {
				indexByDomainIdx.get( keyDomainIdx ).remove( pkey );
				if( indexByDomainIdx.get( keyDomainIdx ).size() <= 0 ) {
					indexByDomainIdx.remove( keyDomainIdx );
				}
			}
		}

		if( indexByUNameIdx != null ) {
			indexByUNameIdx.remove( keyUNameIdx );
		}


	}
	@Override
	public void deepDisposeLicense( CFLibDbKeyHash256 pkey ) {
		ICFIntLicenseObj obj = readCachedLicense( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntLicenseObj lockLicense( CFLibDbKeyHash256 pkey ) {
		ICFIntLicenseObj locked = null;
		ICFIntLicense lockRec = schema.getCFIntBackingStore().getTableLicense().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getLicenseTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntLicenseObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockLicense", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntLicenseObj> readAllLicense() {
		return( readAllLicense( false ) );
	}

	@Override
	public List<ICFIntLicenseObj> readAllLicense( boolean forceRead ) {
		final String S_ProcName = "readAllLicense";
		if( ( allLicense == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFIntLicenseObj> map = new HashMap<CFLibDbKeyHash256,ICFIntLicenseObj>();
			allLicense = map;
			ICFIntLicense[] recList = schema.getCFIntBackingStore().getTableLicense().readAllDerived( null );
			ICFIntLicense rec;
			ICFIntLicenseObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntLicenseObj realised = (ICFIntLicenseObj)obj.realise();
			}
		}
		int len = allLicense.size();
		ICFIntLicenseObj arr[] = new ICFIntLicenseObj[len];
		Iterator<ICFIntLicenseObj> valIter = allLicense.values().iterator();
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
		ArrayList<ICFIntLicenseObj> arrayList = new ArrayList<ICFIntLicenseObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntLicenseObj> cmp = new Comparator<ICFIntLicenseObj>() {
			@Override
			public int compare( ICFIntLicenseObj lhs, ICFIntLicenseObj rhs ) {
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
		List<ICFIntLicenseObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntLicenseObj> readCachedAllLicense() {
		final String S_ProcName = "readCachedAllLicense";
		ArrayList<ICFIntLicenseObj> arrayList = new ArrayList<ICFIntLicenseObj>();
		if( allLicense != null ) {
			int len = allLicense.size();
			ICFIntLicenseObj arr[] = new ICFIntLicenseObj[len];
			Iterator<ICFIntLicenseObj> valIter = allLicense.values().iterator();
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
		Comparator<ICFIntLicenseObj> cmp = new Comparator<ICFIntLicenseObj>() {
			public int compare( ICFIntLicenseObj lhs, ICFIntLicenseObj rhs ) {
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
	public ICFIntLicenseObj readLicenseByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readLicenseByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFIntLicenseObj readLicenseByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFIntLicenseObj obj = readLicense( Id, forceRead );
		return( obj );
	}

	@Override
	public List<ICFIntLicenseObj> readLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readLicenseByLicnTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFIntLicenseObj> readLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readLicenseByLicnTenantIdx";
		ICFIntLicenseByLicnTenantIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByLicnTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFIntLicenseObj> dict;
		if( indexByLicnTenantIdx == null ) {
			indexByLicnTenantIdx = new HashMap< ICFIntLicenseByLicnTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntLicenseObj > >();
		}
		if( ( ! forceRead ) && indexByLicnTenantIdx.containsKey( key ) ) {
			dict = indexByLicnTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntLicenseObj>();
			ICFIntLicenseObj obj;
			ICFIntLicense[] recList = schema.getCFIntBackingStore().getTableLicense().readDerivedByLicnTenantIdx( null,
				TenantId );
			ICFIntLicense rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getLicenseTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntLicenseObj realised = (ICFIntLicenseObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByLicnTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntLicenseObj arr[] = new ICFIntLicenseObj[len];
		Iterator<ICFIntLicenseObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntLicenseObj> arrayList = new ArrayList<ICFIntLicenseObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntLicenseObj> cmp = new Comparator<ICFIntLicenseObj>() {
			@Override
			public int compare( ICFIntLicenseObj lhs, ICFIntLicenseObj rhs ) {
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
		List<ICFIntLicenseObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntLicenseObj> readLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId )
	{
		return( readLicenseByDomainIdx( TopDomainId,
			false ) );
	}

	@Override
	public List<ICFIntLicenseObj> readLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId,
		boolean forceRead )
	{
		final String S_ProcName = "readLicenseByDomainIdx";
		ICFIntLicenseByDomainIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByDomainIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		Map<CFLibDbKeyHash256, ICFIntLicenseObj> dict;
		if( indexByDomainIdx == null ) {
			indexByDomainIdx = new HashMap< ICFIntLicenseByDomainIdxKey,
				Map< CFLibDbKeyHash256, ICFIntLicenseObj > >();
		}
		if( ( ! forceRead ) && indexByDomainIdx.containsKey( key ) ) {
			dict = indexByDomainIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntLicenseObj>();
			ICFIntLicenseObj obj;
			ICFIntLicense[] recList = schema.getCFIntBackingStore().getTableLicense().readDerivedByDomainIdx( null,
				TopDomainId );
			ICFIntLicense rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getLicenseTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntLicenseObj realised = (ICFIntLicenseObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByDomainIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntLicenseObj arr[] = new ICFIntLicenseObj[len];
		Iterator<ICFIntLicenseObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntLicenseObj> arrayList = new ArrayList<ICFIntLicenseObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntLicenseObj> cmp = new Comparator<ICFIntLicenseObj>() {
			@Override
			public int compare( ICFIntLicenseObj lhs, ICFIntLicenseObj rhs ) {
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
		List<ICFIntLicenseObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFIntLicenseObj readLicenseByUNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name )
	{
		return( readLicenseByUNameIdx( TopDomainId,
			Name,
			false ) );
	}

	@Override
	public ICFIntLicenseObj readLicenseByUNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name, boolean forceRead )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFIntLicenseByUNameIdxKey,
				ICFIntLicenseObj >();
		}
		ICFIntLicenseByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByUNameIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		key.setRequiredName( Name );
		ICFIntLicenseObj obj = null;
		if( ( ! forceRead ) && indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
		}
		else {
			ICFIntLicense rec = schema.getCFIntBackingStore().getTableLicense().readDerivedByUNameIdx( null,
				TopDomainId,
				Name );
			if( rec != null ) {
				obj = schema.getLicenseTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntLicenseObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntLicenseObj readCachedLicenseByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntLicenseObj obj = null;
		obj = readCachedLicense( Id );
		return( obj );
	}

	@Override
	public List<ICFIntLicenseObj> readCachedLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedLicenseByLicnTenantIdx";
		ICFIntLicenseByLicnTenantIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByLicnTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFIntLicenseObj> arrayList = new ArrayList<ICFIntLicenseObj>();
		if( indexByLicnTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntLicenseObj> dict;
			if( indexByLicnTenantIdx.containsKey( key ) ) {
				dict = indexByLicnTenantIdx.get( key );
				int len = dict.size();
				ICFIntLicenseObj arr[] = new ICFIntLicenseObj[len];
				Iterator<ICFIntLicenseObj> valIter = dict.values().iterator();
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
			ICFIntLicenseObj obj;
			Iterator<ICFIntLicenseObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntLicenseObj> cmp = new Comparator<ICFIntLicenseObj>() {
			@Override
			public int compare( ICFIntLicenseObj lhs, ICFIntLicenseObj rhs ) {
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
	public List<ICFIntLicenseObj> readCachedLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId )
	{
		final String S_ProcName = "readCachedLicenseByDomainIdx";
		ICFIntLicenseByDomainIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByDomainIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		ArrayList<ICFIntLicenseObj> arrayList = new ArrayList<ICFIntLicenseObj>();
		if( indexByDomainIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntLicenseObj> dict;
			if( indexByDomainIdx.containsKey( key ) ) {
				dict = indexByDomainIdx.get( key );
				int len = dict.size();
				ICFIntLicenseObj arr[] = new ICFIntLicenseObj[len];
				Iterator<ICFIntLicenseObj> valIter = dict.values().iterator();
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
			ICFIntLicenseObj obj;
			Iterator<ICFIntLicenseObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntLicenseObj> cmp = new Comparator<ICFIntLicenseObj>() {
			@Override
			public int compare( ICFIntLicenseObj lhs, ICFIntLicenseObj rhs ) {
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
	public ICFIntLicenseObj readCachedLicenseByUNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name )
	{
		ICFIntLicenseObj obj = null;
		ICFIntLicenseByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByUNameIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		key.setRequiredName( Name );
		if( indexByUNameIdx != null ) {
			if( indexByUNameIdx.containsKey( key ) ) {
				obj = indexByUNameIdx.get( key );
			}
			else {
				Iterator<ICFIntLicenseObj> valIter = members.values().iterator();
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
			Iterator<ICFIntLicenseObj> valIter = members.values().iterator();
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
	public void deepDisposeLicenseByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntLicenseObj obj = readCachedLicenseByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeLicenseByLicnTenantIdx";
		ICFIntLicenseObj obj;
		List<ICFIntLicenseObj> arrayList = readCachedLicenseByLicnTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFIntLicenseObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId )
	{
		final String S_ProcName = "deepDisposeLicenseByDomainIdx";
		ICFIntLicenseObj obj;
		List<ICFIntLicenseObj> arrayList = readCachedLicenseByDomainIdx( TopDomainId );
		if( arrayList != null )  {
			Iterator<ICFIntLicenseObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeLicenseByUNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name )
	{
		ICFIntLicenseObj obj = readCachedLicenseByUNameIdx( TopDomainId,
				Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntLicenseObj updateLicense( ICFIntLicenseObj Obj ) {
		ICFIntLicenseObj obj = Obj;
		schema.getCFIntBackingStore().getTableLicense().updateLicense( null,
			Obj.getLicenseRec() );
		obj = (ICFIntLicenseObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteLicense( ICFIntLicenseObj Obj ) {
		ICFIntLicenseObj obj = Obj;
		schema.getCFIntBackingStore().getTableLicense().deleteLicense( null,
			obj.getLicenseRec() );
		Obj.forget();
	}

	@Override
	public void deleteLicenseByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntLicenseObj obj = readLicense(Id);
		if( obj != null ) {
			ICFIntLicenseEditObj editObj = (ICFIntLicenseEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntLicenseEditObj)obj.beginEdit();
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
		deepDisposeLicenseByIdIdx( Id );
	}

	@Override
	public void deleteLicenseByLicnTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFIntLicenseByLicnTenantIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByLicnTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByLicnTenantIdx == null ) {
			indexByLicnTenantIdx = new HashMap< ICFIntLicenseByLicnTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntLicenseObj > >();
		}
		if( indexByLicnTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntLicenseObj> dict = indexByLicnTenantIdx.get( key );
			schema.getCFIntBackingStore().getTableLicense().deleteLicenseByLicnTenantIdx( null,
				TenantId );
			Iterator<ICFIntLicenseObj> iter = dict.values().iterator();
			ICFIntLicenseObj obj;
			List<ICFIntLicenseObj> toForget = new LinkedList<ICFIntLicenseObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByLicnTenantIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableLicense().deleteLicenseByLicnTenantIdx( null,
				TenantId );
		}
		deepDisposeLicenseByLicnTenantIdx( TenantId );
	}

	@Override
	public void deleteLicenseByDomainIdx( CFLibDbKeyHash256 TopDomainId )
	{
		ICFIntLicenseByDomainIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByDomainIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		if( indexByDomainIdx == null ) {
			indexByDomainIdx = new HashMap< ICFIntLicenseByDomainIdxKey,
				Map< CFLibDbKeyHash256, ICFIntLicenseObj > >();
		}
		if( indexByDomainIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntLicenseObj> dict = indexByDomainIdx.get( key );
			schema.getCFIntBackingStore().getTableLicense().deleteLicenseByDomainIdx( null,
				TopDomainId );
			Iterator<ICFIntLicenseObj> iter = dict.values().iterator();
			ICFIntLicenseObj obj;
			List<ICFIntLicenseObj> toForget = new LinkedList<ICFIntLicenseObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByDomainIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableLicense().deleteLicenseByDomainIdx( null,
				TopDomainId );
		}
		deepDisposeLicenseByDomainIdx( TopDomainId );
	}

	@Override
	public void deleteLicenseByUNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFIntLicenseByUNameIdxKey,
				ICFIntLicenseObj >();
		}
		ICFIntLicenseByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryLicense().newByUNameIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		key.setRequiredName( Name );
		ICFIntLicenseObj obj = null;
		if( indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
			schema.getCFIntBackingStore().getTableLicense().deleteLicenseByUNameIdx( null,
				TopDomainId,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableLicense().deleteLicenseByUNameIdx( null,
				TopDomainId,
				Name );
		}
		deepDisposeLicenseByUNameIdx( TopDomainId,
				Name );
	}
}