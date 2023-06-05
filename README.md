# PortableHorses
Minecraft plugin built for spigot 1.12-1.16, adds functionality to improve upon vanilla horse system. Worldguard integration included.


![Portablehorsesbanner](https://i.ibb.co/HLZKndg/porthores.png) <span style="font-size: 15px">Keeping track of your horse while traveling the world can be tedious, Portable Horses offers a way to keep your horse with you at all times during your adventure.<br>
<br>
After dismounting your horse, they will be neatly packaged into their saddle which you can use at any time to summon your beloved steed.<br>
</span><br>
<div class="ToggleTriggerAnchor bbCodeSpoilerContainer">
<br>
The saddle dropped by your horse will be colored and marked with the horses name to make sure you're summoning the right horse.<br>
<br>
  
  
![Portable Horse Example](https://i.imgur.com2F6gl0RKl.png)
  
  
</div><b><span style="text-decoration: underline"><span style="font-size: 22px"><span style="color: rgb(77, 166, 255); --darkreader-inline-color: #4fb0ff;" data-darkreader-inline-color="">Commands:</span></span></span></b><br>
<div class="ToggleTriggerAnchor bbCodeSpoilerContainer">
<button type="button" class="button bbCodeSpoilerButton ToggleTrigger Tooltip JsOnly" data-target="> .SpoilerTarget"><span>Spoiler: <span class="SpoilerTitle">Commands</span></span></button>
<div class="SpoilerTarget bbCodeSpoilerText"><ul>
<li><div style="text-align: left">call​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(188, 182, 173); --darkreader-inline-color: #b9b3aa;" data-darkreader-inline-color="">Usage: /ph call</span>​</div></li>
<li><div style="text-align: left"><span style="color: rgb(188, 182, 173); --darkreader-inline-color: #b9b3aa;" data-darkreader-inline-color="">will teleport your horse to you provided it is within the distance set in the config, and is not being ridden.</span>​</div></li>
</ul></li>
</ul></div>
</div><b><span style="text-decoration: underline"><span style="font-size: 22px"><span style="color: rgb(77, 166, 255); --darkreader-inline-color: #4fb0ff;" data-darkreader-inline-color="">Permissions:</span></span></span></b><br>
<div class="ToggleTriggerAnchor bbCodeSpoilerContainer">
<button type="button" class="button bbCodeSpoilerButton ToggleTrigger Tooltip JsOnly" data-target="> .SpoilerTarget"><span>Spoiler: <span class="SpoilerTitle">Permissions</span></span></button>
<div class="SpoilerTarget bbCodeSpoilerText"><ul>
<li><div style="text-align: left">portablehorses.create​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">Determines whether or not a player will create a saddle upon dismount.</span>​</div></li>
</ul></li>
<li><div style="text-align: left">portablehorses.use​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">Determines whether or not a player can use a portable horse saddle</span>​</div></li>
</ul></li>
<li><div style="text-align: left">portablehorses.call​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">Determines whether or not a player can use the call command.</span>​</div></li>
</ul></li>
<li><div style="text-align: left">portablehorses.reload​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">needed to reload plugin config.</span>​</div></li>
</ul></li>
</ul></div>
</div><b><span style="text-decoration: underline"><span style="font-size: 22px"><span style="color: rgb(77, 166, 255); --darkreader-inline-color: #4fb0ff;" data-darkreader-inline-color="">Config:</span></span></span></b><br>
<div class="ToggleTriggerAnchor bbCodeSpoilerContainer">
<button type="button" class="button bbCodeSpoilerButton ToggleTrigger Tooltip JsOnly" data-target="> .SpoilerTarget"><span>Spoiler: <span class="SpoilerTitle">Config Settings</span></span></button>
<div class="SpoilerTarget bbCodeSpoilerText"><ul>
<li><div style="text-align: left">PermissionsDefault​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">Sets if players have the ability to use the plugin's features by default. Set to false if you want to allow access to only certain users/groups. This is set to true by default</span>​</div></li>
</ul></li>
<li><div style="text-align: left">CombatCooldown​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">how quickly a player will be able to summon their horse after entering combat (0 for disabled).</span>​</div></li>
</ul></li>
<li><div style="text-align: left">SummonCooldown​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">determines the delay for summoning a horse after it is put inside a saddle (0 for disabled).</span>​</div></li>
</ul></li>
<li><div style="text-align: left">AutoMount​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">if true, players will automatically mount their horse on summon.</span>​</div></li>
</ul></li>
<li><div style="text-align: left">CallDistance​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">max distance a horse can be called from in blocks</span>​</div></li>
</ul></li>
<li><div style="text-align: left">MessagePrefix​</div><ul>
<li><div style="text-align: left"><span style="color: rgb(191, 191, 191); --darkreader-inline-color: #c0bab2;" data-darkreader-inline-color="">Enable if you want to have the prefix [Portable Horses] before messages created by the plugin.</span>​</div></li>
</ul></li>
</ul></div>
</div><br>
Thank you to the Following Spigot users for helping translate the plugin<br>
​HongMint:


    Korean translation.
    
    
Cantibra:


    German translation.
