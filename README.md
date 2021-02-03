# Status
修改自LoadSir【https://github.com/KingJA/LoadSir】
由于已有项目内已有LoadSir，自用所以改名，使用方法与LoadSir相同 
1.去除shouDefaultCallback时候的handler导致直接使用showSuccess出现的问题【库内使用post导致了先调用showSuccess后调用showLoading】
2.添加了切换界面的渐变动画

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
	dependencies {
	        implementation 'com.github.zizikoth:Status:a.b.c'
	}
```
