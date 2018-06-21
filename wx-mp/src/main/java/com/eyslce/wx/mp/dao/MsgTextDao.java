/*
 * FileName：MsgTextDao.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.MsgText;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MsgTextDao {

	public MsgText getById(String id);

	public List<MsgText> getMsgTextByPage(MsgText searchEntity);

	public List<MsgText> getMsgTextList(MsgText searchEntity);

	public void add(MsgText entity);

	public void update(MsgText entity);

	public void delete(MsgText entity);

	public MsgText getRandomMsg(String inputCode);
	
	public MsgText getRandomMsg2();

	public MsgText getByBaseId(String baseid);
}
