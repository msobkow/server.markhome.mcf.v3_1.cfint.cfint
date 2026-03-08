// Description: Java 25 Table Object implementation for TopProject.

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

public class CFIntTopProjectTableObj
	implements ICFIntTopProjectTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntTopProject.CLASS_CODE;
	protected static final int backingClassCode = ICFIntTopProject.CLASS_CODE;
	private Map<CFLibDbKeyHash256, ICFIntTopProjectObj> members;
	private Map<CFLibDbKeyHash256, ICFIntTopProjectObj> allTopProject;
	private Map< ICFIntTopProjectByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFIntTopProjectObj > > indexByTenantIdx;
	private Map< ICFIntTopProjectByTopDomainIdxKey,
		Map<CFLibDbKeyHash256, ICFIntTopProjectObj > > indexByTopDomainIdx;
	private Map< ICFIntTopProjectByNameIdxKey,
		ICFIntTopProjectObj > indexByNameIdx;
	public static String TABLE_NAME = "TopProject";
	public static String TABLE_DBNAME = "tprjdef";

	public CFIntTopProjectTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFIntTopProjectObj>();
		allTopProject = null;
		indexByTenantIdx = null;
		indexByTopDomainIdx = null;
		indexByNameIdx = null;
	}

	public CFIntTopProjectTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFIntTopProjectObj>();
		allTopProject = null;
		indexByTenantIdx = null;
		indexByTopDomainIdx = null;
		indexByNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntTopProjectTableObj.getRuntimeClassCode();
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
			throw new CFLibArgumentUnderflowException(CFIntTopProjectTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
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
		allTopProject = null;
		indexByTenantIdx = null;
		indexByTopDomainIdx = null;
		indexByNameIdx = null;
		List<ICFIntTopProjectObj> toForget = new LinkedList<ICFIntTopProjectObj>();
		ICFIntTopProjectObj cur = null;
		Iterator<ICFIntTopProjectObj> iter = members.values().iterator();
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
	 *	CFIntTopProjectObj.
	 */
	@Override
	public ICFIntTopProjectObj newInstance() {
		ICFIntTopProjectObj inst = new CFIntTopProjectObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntTopProjectObj.
	 */
	@Override
	public ICFIntTopProjectEditObj newEditInstance( ICFIntTopProjectObj orig ) {
		ICFIntTopProjectEditObj edit = new CFIntTopProjectEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntTopProjectObj realiseTopProject( ICFIntTopProjectObj Obj ) {
		ICFIntTopProjectObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntTopProjectObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntTopProjectObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFIntTopProjectByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTopProjectObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexByTopDomainIdx != null ) {
				ICFIntTopProjectByTopDomainIdxKey keyTopDomainIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByTopDomainIdxKey();
				keyTopDomainIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				Map<CFLibDbKeyHash256, ICFIntTopProjectObj > mapTopDomainIdx = indexByTopDomainIdx.get( keyTopDomainIdx );
				if( mapTopDomainIdx != null ) {
					mapTopDomainIdx.remove( keepObj.getPKey() );
					if( mapTopDomainIdx.size() <= 0 ) {
						indexByTopDomainIdx.remove( keyTopDomainIdx );
					}
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTopProjectByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByNameIdxKey();
				keyNameIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.remove( keyNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFIntTopProjectByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTopProjectObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTopDomainIdx != null ) {
				ICFIntTopProjectByTopDomainIdxKey keyTopDomainIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByTopDomainIdxKey();
				keyTopDomainIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				Map<CFLibDbKeyHash256, ICFIntTopProjectObj > mapTopDomainIdx = indexByTopDomainIdx.get( keyTopDomainIdx );
				if( mapTopDomainIdx != null ) {
					mapTopDomainIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTopProjectByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByNameIdxKey();
				keyNameIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

			if( allTopProject != null ) {
				allTopProject.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allTopProject != null ) {
				allTopProject.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFIntTopProjectByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTopProjectObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTopDomainIdx != null ) {
				ICFIntTopProjectByTopDomainIdxKey keyTopDomainIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByTopDomainIdxKey();
				keyTopDomainIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				Map<CFLibDbKeyHash256, ICFIntTopProjectObj > mapTopDomainIdx = indexByTopDomainIdx.get( keyTopDomainIdx );
				if( mapTopDomainIdx != null ) {
					mapTopDomainIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTopProjectByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTopProject().newByNameIdxKey();
				keyNameIdx.setRequiredTopDomainId( keepObj.getRequiredTopDomainId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntTopProjectObj createTopProject( ICFIntTopProjectObj Obj ) {
		ICFIntTopProjectObj obj = Obj;
		ICFIntTopProject rec = obj.getTopProjectRec();
		schema.getCFIntBackingStore().getTableTopProject().createTopProject(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntTopProjectObj readTopProject( CFLibDbKeyHash256 pkey ) {
		return( readTopProject( pkey, false ) );
	}

	@Override
	public ICFIntTopProjectObj readTopProject( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFIntTopProjectObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntTopProject readRec = schema.getCFIntBackingStore().getTableTopProject().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getTopProjectTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntTopProjectObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntTopProjectObj readCachedTopProject( CFLibDbKeyHash256 pkey ) {
		ICFIntTopProjectObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeTopProject( ICFIntTopProjectObj obj )
	{
		final String S_ProcName = "CFIntTopProjectTableObj.reallyDeepDisposeTopProject() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntTopProjectObj existing = readCachedTopProject( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntTopProjectByTenantIdxKey keyTenantIdx = schema.getCFIntBackingStore().getFactoryTopProject().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFIntTopProjectByTopDomainIdxKey keyTopDomainIdx = schema.getCFIntBackingStore().getFactoryTopProject().newByTopDomainIdxKey();
		keyTopDomainIdx.setRequiredTopDomainId( existing.getRequiredTopDomainId() );

		ICFIntTopProjectByNameIdxKey keyNameIdx = schema.getCFIntBackingStore().getFactoryTopProject().newByNameIdxKey();
		keyNameIdx.setRequiredTopDomainId( existing.getRequiredTopDomainId() );
		keyNameIdx.setRequiredName( existing.getRequiredName() );


					schema.getSubProjectTableObj().deepDisposeSubProjectByTopProjectIdx( existing.getRequiredId() );

		if( indexByTenantIdx != null ) {
			if( indexByTenantIdx.containsKey( keyTenantIdx ) ) {
				indexByTenantIdx.get( keyTenantIdx ).remove( pkey );
				if( indexByTenantIdx.get( keyTenantIdx ).size() <= 0 ) {
					indexByTenantIdx.remove( keyTenantIdx );
				}
			}
		}

		if( indexByTopDomainIdx != null ) {
			if( indexByTopDomainIdx.containsKey( keyTopDomainIdx ) ) {
				indexByTopDomainIdx.get( keyTopDomainIdx ).remove( pkey );
				if( indexByTopDomainIdx.get( keyTopDomainIdx ).size() <= 0 ) {
					indexByTopDomainIdx.remove( keyTopDomainIdx );
				}
			}
		}

		if( indexByNameIdx != null ) {
			indexByNameIdx.remove( keyNameIdx );
		}


	}
	@Override
	public void deepDisposeTopProject( CFLibDbKeyHash256 pkey ) {
		ICFIntTopProjectObj obj = readCachedTopProject( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntTopProjectObj lockTopProject( CFLibDbKeyHash256 pkey ) {
		ICFIntTopProjectObj locked = null;
		ICFIntTopProject lockRec = schema.getCFIntBackingStore().getTableTopProject().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getTopProjectTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntTopProjectObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockTopProject", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntTopProjectObj> readAllTopProject() {
		return( readAllTopProject( false ) );
	}

	@Override
	public List<ICFIntTopProjectObj> readAllTopProject( boolean forceRead ) {
		final String S_ProcName = "readAllTopProject";
		if( ( allTopProject == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFIntTopProjectObj> map = new HashMap<CFLibDbKeyHash256,ICFIntTopProjectObj>();
			allTopProject = map;
			ICFIntTopProject[] recList = schema.getCFIntBackingStore().getTableTopProject().readAllDerived( null );
			ICFIntTopProject rec;
			ICFIntTopProjectObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntTopProjectObj realised = (ICFIntTopProjectObj)obj.realise();
			}
		}
		int len = allTopProject.size();
		ICFIntTopProjectObj arr[] = new ICFIntTopProjectObj[len];
		Iterator<ICFIntTopProjectObj> valIter = allTopProject.values().iterator();
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
		ArrayList<ICFIntTopProjectObj> arrayList = new ArrayList<ICFIntTopProjectObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntTopProjectObj> cmp = new Comparator<ICFIntTopProjectObj>() {
			@Override
			public int compare( ICFIntTopProjectObj lhs, ICFIntTopProjectObj rhs ) {
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
		List<ICFIntTopProjectObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntTopProjectObj> readCachedAllTopProject() {
		final String S_ProcName = "readCachedAllTopProject";
		ArrayList<ICFIntTopProjectObj> arrayList = new ArrayList<ICFIntTopProjectObj>();
		if( allTopProject != null ) {
			int len = allTopProject.size();
			ICFIntTopProjectObj arr[] = new ICFIntTopProjectObj[len];
			Iterator<ICFIntTopProjectObj> valIter = allTopProject.values().iterator();
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
		Comparator<ICFIntTopProjectObj> cmp = new Comparator<ICFIntTopProjectObj>() {
			public int compare( ICFIntTopProjectObj lhs, ICFIntTopProjectObj rhs ) {
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
	public ICFIntTopProjectObj readTopProjectByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readTopProjectByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFIntTopProjectObj readTopProjectByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFIntTopProjectObj obj = readTopProject( Id, forceRead );
		return( obj );
	}

	@Override
	public List<ICFIntTopProjectObj> readTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readTopProjectByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFIntTopProjectObj> readTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readTopProjectByTenantIdx";
		ICFIntTopProjectByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFIntTopProjectObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntTopProjectByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTopProjectObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntTopProjectObj>();
			ICFIntTopProjectObj obj;
			ICFIntTopProject[] recList = schema.getCFIntBackingStore().getTableTopProject().readDerivedByTenantIdx( null,
				TenantId );
			ICFIntTopProject rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTopProjectTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntTopProjectObj realised = (ICFIntTopProjectObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntTopProjectObj arr[] = new ICFIntTopProjectObj[len];
		Iterator<ICFIntTopProjectObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntTopProjectObj> arrayList = new ArrayList<ICFIntTopProjectObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntTopProjectObj> cmp = new Comparator<ICFIntTopProjectObj>() {
			@Override
			public int compare( ICFIntTopProjectObj lhs, ICFIntTopProjectObj rhs ) {
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
		List<ICFIntTopProjectObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntTopProjectObj> readTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId )
	{
		return( readTopProjectByTopDomainIdx( TopDomainId,
			false ) );
	}

	@Override
	public List<ICFIntTopProjectObj> readTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId,
		boolean forceRead )
	{
		final String S_ProcName = "readTopProjectByTopDomainIdx";
		ICFIntTopProjectByTopDomainIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByTopDomainIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		Map<CFLibDbKeyHash256, ICFIntTopProjectObj> dict;
		if( indexByTopDomainIdx == null ) {
			indexByTopDomainIdx = new HashMap< ICFIntTopProjectByTopDomainIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTopProjectObj > >();
		}
		if( ( ! forceRead ) && indexByTopDomainIdx.containsKey( key ) ) {
			dict = indexByTopDomainIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntTopProjectObj>();
			ICFIntTopProjectObj obj;
			ICFIntTopProject[] recList = schema.getCFIntBackingStore().getTableTopProject().readDerivedByTopDomainIdx( null,
				TopDomainId );
			ICFIntTopProject rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTopProjectTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntTopProjectObj realised = (ICFIntTopProjectObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTopDomainIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntTopProjectObj arr[] = new ICFIntTopProjectObj[len];
		Iterator<ICFIntTopProjectObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntTopProjectObj> arrayList = new ArrayList<ICFIntTopProjectObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntTopProjectObj> cmp = new Comparator<ICFIntTopProjectObj>() {
			@Override
			public int compare( ICFIntTopProjectObj lhs, ICFIntTopProjectObj rhs ) {
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
		List<ICFIntTopProjectObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFIntTopProjectObj readTopProjectByNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name )
	{
		return( readTopProjectByNameIdx( TopDomainId,
			Name,
			false ) );
	}

	@Override
	public ICFIntTopProjectObj readTopProjectByNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name, boolean forceRead )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntTopProjectByNameIdxKey,
				ICFIntTopProjectObj >();
		}
		ICFIntTopProjectByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByNameIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		key.setRequiredName( Name );
		ICFIntTopProjectObj obj = null;
		if( ( ! forceRead ) && indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
		}
		else {
			ICFIntTopProject rec = schema.getCFIntBackingStore().getTableTopProject().readDerivedByNameIdx( null,
				TopDomainId,
				Name );
			if( rec != null ) {
				obj = schema.getTopProjectTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntTopProjectObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntTopProjectObj readCachedTopProjectByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTopProjectObj obj = null;
		obj = readCachedTopProject( Id );
		return( obj );
	}

	@Override
	public List<ICFIntTopProjectObj> readCachedTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedTopProjectByTenantIdx";
		ICFIntTopProjectByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFIntTopProjectObj> arrayList = new ArrayList<ICFIntTopProjectObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntTopProjectObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFIntTopProjectObj arr[] = new ICFIntTopProjectObj[len];
				Iterator<ICFIntTopProjectObj> valIter = dict.values().iterator();
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
			ICFIntTopProjectObj obj;
			Iterator<ICFIntTopProjectObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntTopProjectObj> cmp = new Comparator<ICFIntTopProjectObj>() {
			@Override
			public int compare( ICFIntTopProjectObj lhs, ICFIntTopProjectObj rhs ) {
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
	public List<ICFIntTopProjectObj> readCachedTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId )
	{
		final String S_ProcName = "readCachedTopProjectByTopDomainIdx";
		ICFIntTopProjectByTopDomainIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByTopDomainIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		ArrayList<ICFIntTopProjectObj> arrayList = new ArrayList<ICFIntTopProjectObj>();
		if( indexByTopDomainIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntTopProjectObj> dict;
			if( indexByTopDomainIdx.containsKey( key ) ) {
				dict = indexByTopDomainIdx.get( key );
				int len = dict.size();
				ICFIntTopProjectObj arr[] = new ICFIntTopProjectObj[len];
				Iterator<ICFIntTopProjectObj> valIter = dict.values().iterator();
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
			ICFIntTopProjectObj obj;
			Iterator<ICFIntTopProjectObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntTopProjectObj> cmp = new Comparator<ICFIntTopProjectObj>() {
			@Override
			public int compare( ICFIntTopProjectObj lhs, ICFIntTopProjectObj rhs ) {
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
	public ICFIntTopProjectObj readCachedTopProjectByNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name )
	{
		ICFIntTopProjectObj obj = null;
		ICFIntTopProjectByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByNameIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		key.setRequiredName( Name );
		if( indexByNameIdx != null ) {
			if( indexByNameIdx.containsKey( key ) ) {
				obj = indexByNameIdx.get( key );
			}
			else {
				Iterator<ICFIntTopProjectObj> valIter = members.values().iterator();
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
			Iterator<ICFIntTopProjectObj> valIter = members.values().iterator();
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
	public void deepDisposeTopProjectByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTopProjectObj obj = readCachedTopProjectByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeTopProjectByTenantIdx";
		ICFIntTopProjectObj obj;
		List<ICFIntTopProjectObj> arrayList = readCachedTopProjectByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFIntTopProjectObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId )
	{
		final String S_ProcName = "deepDisposeTopProjectByTopDomainIdx";
		ICFIntTopProjectObj obj;
		List<ICFIntTopProjectObj> arrayList = readCachedTopProjectByTopDomainIdx( TopDomainId );
		if( arrayList != null )  {
			Iterator<ICFIntTopProjectObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTopProjectByNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name )
	{
		ICFIntTopProjectObj obj = readCachedTopProjectByNameIdx( TopDomainId,
				Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntTopProjectObj updateTopProject( ICFIntTopProjectObj Obj ) {
		ICFIntTopProjectObj obj = Obj;
		schema.getCFIntBackingStore().getTableTopProject().updateTopProject( null,
			Obj.getTopProjectRec() );
		obj = (ICFIntTopProjectObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteTopProject( ICFIntTopProjectObj Obj ) {
		ICFIntTopProjectObj obj = Obj;
		schema.getCFIntBackingStore().getTableTopProject().deleteTopProject( null,
			obj.getTopProjectRec() );
		Obj.forget();
	}

	@Override
	public void deleteTopProjectByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTopProjectObj obj = readTopProject(Id);
		if( obj != null ) {
			ICFIntTopProjectEditObj editObj = (ICFIntTopProjectEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntTopProjectEditObj)obj.beginEdit();
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
		deepDisposeTopProjectByIdIdx( Id );
	}

	@Override
	public void deleteTopProjectByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFIntTopProjectByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntTopProjectByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTopProjectObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntTopProjectObj> dict = indexByTenantIdx.get( key );
			schema.getCFIntBackingStore().getTableTopProject().deleteTopProjectByTenantIdx( null,
				TenantId );
			Iterator<ICFIntTopProjectObj> iter = dict.values().iterator();
			ICFIntTopProjectObj obj;
			List<ICFIntTopProjectObj> toForget = new LinkedList<ICFIntTopProjectObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByTenantIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableTopProject().deleteTopProjectByTenantIdx( null,
				TenantId );
		}
		deepDisposeTopProjectByTenantIdx( TenantId );
	}

	@Override
	public void deleteTopProjectByTopDomainIdx( CFLibDbKeyHash256 TopDomainId )
	{
		ICFIntTopProjectByTopDomainIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByTopDomainIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		if( indexByTopDomainIdx == null ) {
			indexByTopDomainIdx = new HashMap< ICFIntTopProjectByTopDomainIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTopProjectObj > >();
		}
		if( indexByTopDomainIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntTopProjectObj> dict = indexByTopDomainIdx.get( key );
			schema.getCFIntBackingStore().getTableTopProject().deleteTopProjectByTopDomainIdx( null,
				TopDomainId );
			Iterator<ICFIntTopProjectObj> iter = dict.values().iterator();
			ICFIntTopProjectObj obj;
			List<ICFIntTopProjectObj> toForget = new LinkedList<ICFIntTopProjectObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByTopDomainIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableTopProject().deleteTopProjectByTopDomainIdx( null,
				TopDomainId );
		}
		deepDisposeTopProjectByTopDomainIdx( TopDomainId );
	}

	@Override
	public void deleteTopProjectByNameIdx( CFLibDbKeyHash256 TopDomainId,
		String Name )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntTopProjectByNameIdxKey,
				ICFIntTopProjectObj >();
		}
		ICFIntTopProjectByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTopProject().newByNameIdxKey();
		key.setRequiredTopDomainId( TopDomainId );
		key.setRequiredName( Name );
		ICFIntTopProjectObj obj = null;
		if( indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
			schema.getCFIntBackingStore().getTableTopProject().deleteTopProjectByNameIdx( null,
				TopDomainId,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableTopProject().deleteTopProjectByNameIdx( null,
				TopDomainId,
				Name );
		}
		deepDisposeTopProjectByNameIdx( TopDomainId,
				Name );
	}
}