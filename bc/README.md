#ETH客户端
<table border="1" class="docutils">
<colgroup>
<col width="25%">
<col width="12%">
<col width="25%">
<col width="38%">
</colgroup>
<thead valign="bottom">
<tr class="row-odd"><th class="head">Client</th>
<th class="head">Language</th>
<th class="head">Developers</th>
<th class="head">Latest release</th>
</tr>
</thead>
<tbody valign="top">
<tr class="row-even"><td><a class="reference internal" href="go-ethereum/index.html#go-ethereum"><span>go-ethereum</span></a></td>
<td>Go</td>
<td><a class="reference external" href="https://ethereum.org/foundation">Ethereum Foundation</a></td>
<td><a class="reference external" href="https://github.com/ethereum/go-ethereum/releases/tag/v1.4.18">go-ethereum-v1.4.18</a></td>
</tr>
<tr class="row-odd"><td><a class="reference internal" href="parity/index.html#parity"><span>Parity</span></a></td>
<td>Rust</td>
<td><a class="reference external" href="https://ethcore.io/">Ethcore</a></td>
<td><a class="reference external" href="https://github.com/ethcore/parity/releases/tag/v1.4.0">Parity-v1.4.0</a></td>
</tr>
<tr class="row-even"><td><a class="reference internal" href="cpp-ethereum/index.html#cpp-ethereum"><span>cpp-ethereum</span></a></td>
<td>C++</td>
<td><a class="reference external" href="https://ethereum.org/foundation">Ethereum Foundation</a></td>
<td><a class="reference external" href="https://github.com/bobsummerwill/cpp-ethereum/releases/tag/v1.3.0">cpp-ethereum-v1.3.0</a></td>
</tr>
<tr class="row-odd"><td><a class="reference internal" href="pyethapp/index.html#pyethapp"><span>pyethapp</span></a></td>
<td>Python</td>
<td><a class="reference external" href="https://ethereum.org/foundation">Ethereum Foundation</a></td>
<td><a class="reference external" href="https://github.com/ethereum/pyethapp/releases/tag/v1.5.0">pyethapp-v1.5.0</a></td>
</tr>
<tr class="row-even"><td><a class="reference internal" href="ethereumjs-lib/index.html#ethereumjs-lib"><span>ethereumjs-lib</span></a></td>
<td>Javascript</td>
<td><a class="reference external" href="https://ethereum.org/foundation">Ethereum Foundation</a></td>
<td><a class="reference external" href="https://github.com/ethereumjs/ethereumjs-lib/releases/tag/v3.0.0">ethereumjs-lib-v3.0.0</a></td>
</tr>
<tr class="row-odd"><td><a class="reference internal" href="ethereumj/index.html#ethereum-j"><span>Ethereum(J)</span></a></td>
<td>Java</td>
<td><a class="reference external" href="http://www.ether.camp">&lt;ether.camp&gt;</a></td>
<td><a class="reference external" href="https://github.com/ethereum/ethereumj/releases/tag/1.3.1">ethereumJ-v1.3.1</a></td>
</tr>
<tr class="row-even"><td><a class="reference internal" href="ruby-ethereum/index.html#ruby-ethereum"><span>ruby-ethereum</span></a></td>
<td>Ruby</td>
<td><a class="reference external" href="https://github.com/janx/">Jan Xie</a></td>
<td><a class="reference external" href="https://rubygems.org/gems/ruby-ethereum/versions/0.9.6">ruby-ethereum-v0.9.6</a></td>
</tr>
<tr class="row-odd"><td><a class="reference internal" href="ethereumh/index.html#ethereumh"><span>ethereumH</span></a></td>
<td>Haskell</td>
<td><a class="reference external" href="http://www.blockapps.net/">BlockApps</a></td>
<td>no Homestead release yet</td>
</tr>
</tbody>
</table>
目前领先的实现是go-ethereum 和 Parity

## 与Ethereum客户端集成
目前实现的库有
<table border="1" class="docutils">
<colgroup>
<col width="27%">
<col width="15%">
<col width="58%">
</colgroup>
<thead valign="bottom">
<tr class="row-odd"><th class="head">Library</th>
<th class="head">Language</th>
<th class="head">Project Page</th>
</tr>
</thead>
<tbody valign="top">
<tr class="row-even"><td><a class="reference internal" href="web3.js/index.html#web3-js"><span>web3.js</span></a></td>
<td>JavaScript</td>
<td><a class="reference external" href="https://github.com/ethereum/web3.js">https://github.com/ethereum/web3.js</a></td>
</tr>
<tr class="row-odd"><td><a class="reference internal" href="web3j/index.html#web3j"><span>web3j</span></a></td>
<td>Java</td>
<td><a class="reference external" href="https://github.com/web3j/web3j">https://github.com/web3j/web3j</a></td>
</tr>
<tr class="row-even"><td><a class="reference internal" href="nethereum/index.html#nethereum"><span>Nethereum</span></a></td>
<td>C# .NET</td>
<td><a class="reference external" href="https://github.com/Nethereum/Nethereum">https://github.com/Nethereum/Nethereum</a></td>
</tr>
<tr class="row-odd"><td><a class="reference internal" href="ethereum-ruby/index.html#ethereum-ruby"><span>ethereum-ruby</span></a></td>
<td>Ruby</td>
<td><a class="reference external" href="https://github.com/DigixGlobal/ethereum-ruby">https://github.com/DigixGlobal/ethereum-ruby</a></td>
</tr>
</tbody>
</table>
<p>web3j是一个轻量级Java库，用于与以太网网络上的客户端（节点）进行集成。</p>   
<dl class="docutils">
<dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">核心功能：</font></font></dt>
<dd><ul class="first last simple">
<li><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">通过Java类型与通过JSON-RPC的以太坊客户端进行交互</font></font></li>
<li><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">支持所有JSON-RPC方法类型</font></font></li>
<li><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">支持所有的Geth和Parity方法来管理账户和签署交易</font></font></li>
<li><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">异步和同步发送客户端请求</font></font></li>
<li><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">从Solidity ABI文件自动生成Java智能合约函数包装器</font></font></li>
</ul>
</dd>
</dl>
<p>目前，支持go-ethereum和Parity客户端</p>