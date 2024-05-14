# backgroundrun

I'm glad this is helpful for your projects.

Could you help me with a donation to continue improving and providing support? With your help, I'll be able to start working on the iOS part.
Many users use this type of plugins for their companies' or clients' projects. Your donation would be greatly appreciated.

******* Donation *******

https://buymeacoffee.com/alicarlomow

******* ☕☕☕ *******

Available only for Android.


This plugin starts a service to continue reporting the location. Once started, you must stop it with one of our methods, as even if you close the application, the service will continue running.
Always remember to inform the user about the use of their location, provide the option to view the permissions granted so that the user can review and accept or deny them. You can use our methods.

Usage of this plugin:

Step 1:

Add the following permissions in your project's AndroidManifest.xml:

![image](https://github.com/alicarlo/Capacitor-BackGround-Run-GPS-by-Alicarlo/assets/27228316/9eb1dd60-603c-48d3-80cb-29fccecbd018)

Step 2:

Add your package name in the manifest tag of your AndroidManifest.xml.

Example:

![image](https://github.com/alicarlo/Capacitor-BackGround-Run-GPS-by-Alicarlo/assets/27228316/0c35a9cf-af06-44af-8c2f-d1ca297a2bfb)


Step 3:

Add the following line inside the <application/> tag in your AndroidManifest.xml:

Line: 
<service android:name="io.backgroundrun.test1.BackgroundService" android:enabled="true" android:exported="true"/>

Example:

![image](https://github.com/alicarlo/Capacitor-BackGround-Run-GPS-by-Alicarlo/assets/27228316/3fe56d00-6518-4fc9-9b9b-b9de55a2d152)

Step 4:

To display the service activation notification, it is required to add the following images for its icons in the following path:

android/app/src/main/res/drawable

There are two images and they must be named as follows:

ic_notification_dark.png
ic_notification.png

Example:

![image](https://github.com/alicarlo/Capacitor-BackGround-Run-GPS-by-Alicarlo/assets/27228316/8c3ee832-890f-4b1d-aa5c-35b48498040b)

Step 5:

Notification permissions must be granted.

Note: If you do not grant permission, the service will not run.

You can use one of our methods or use the plugin of your choice.

Permission request: requestNotificationPermission()
Permission check: checkNotificationPermission()
Navigate to notification settings: openNotificationSettings()

Step 6:

Background location permissions must be granted.

Note: If you do not grant permission, the service will not run.

You can use one of our methods or use the plugin of your choice.

Permission check: checkPermissionsService()

The checkPermissionsService() method requires a boolean parameter.
If the parameter is true, a message will be displayed to navigate to location permissions for the user to grant it.

The permission must be set to "Allow all the time".

Example:

![image](https://github.com/alicarlo/Capacitor-BackGround-Run-GPS-by-Alicarlo/assets/27228316/40e1b2ef-50b2-40c7-a4b6-c1cc31f0f622)

![image](https://github.com/alicarlo/Capacitor-BackGround-Run-GPS-by-Alicarlo/assets/27228316/314c0c09-4c9a-41e8-91ae-de48fbb74eb8)

Step 7:

The WakeLock option must be activated.

Use the following method: acquireWakeLock().

This option keeps the service running when the screen is off.

Note: We recommend activating this option before starting the service.

Step 8:

Once the above steps are completed, you can start the service with the following method: showNotificationOnAppClose().

The method supports the following values:

url: You must send the URL (API) of your service where you will receive the coordinates. Your API must be a POST request.
id1, id2, id3, id4: These are optional values in case you need to send any ID, etc.
title: The name that will be displayed in the notification.
timerGps: The time interval at which the service will run. The minimum required time is 30 seconds. 1000 = 1 second.
coordinatesShow: Indicates whether you want to show the coordinates in the notification.
timeShow: Indicates whether you want to show the GPS update time in the notification.


    url: 'https://your-api',
    id1: '',
    id2: '',
    id3: '',
    id4: '',
    title: 'Hello I'm a background service :D',
    timerGps: 30000, // 60000
    coordinatesShow: true,
    timeShow: true


interface GpsOptions 

    url: string;
    id1: string;
    id2: string;
    id3: string;
    id4: string;
    title: string;
    timerGps: number;
    coordinatesShow: boolean;
    timeShow: boolean;


![image](https://github.com/alicarlo/Capacitor-BackGround-Run-GPS-by-Alicarlo/assets/27228316/8bda0a36-b56d-4d5b-96ad-3021442030b5)


Example: 

![image](https://github.com/alicarlo/Capacitor-BackGround-Run-GPS-by-Alicarlo/assets/27228316/0231bff4-4f37-4b32-a441-08bd1468d234)



Step 9:

You should have the service running when you consider it necessary in your project. To stop the service, use the following method: stopNotificationService().


Step 10 (Optional):

The main issue with this type of plugins is resource consumption. We have a method to disable battery optimization. This method is not required with the previous steps; it should work correctly, but some devices may stop the service, which could be prevented by disabling battery optimization.

Always remember to inform the user why this action is required.

Method to disable battery optimization: ignoringBatteryOptimizationsService()
Method to request battery optimization: requestBatteryOptimizations()

Quick summary:

Notification permission.
Background location permission (allow all the time).
WakeLock activation.
Start service.
Stop service (when required).
## Install

```bash
npm install backgroundrun
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`addListener(string, ...)`](#addlistenerstring-)
* [`showNotificationOnAppClose(...)`](#shownotificationonappclose)
* [`stopNotificationService()`](#stopnotificationservice)
* [`checkPermissionsService(...)`](#checkpermissionsservice)
* [`ignoringBatteryOptimizationsService()`](#ignoringbatteryoptimizationsservice)
* [`requestBatteryOptimizations()`](#requestbatteryoptimizations)
* [`requestNotificationPermission()`](#requestnotificationpermission)
* [`checkUsageStatsNotificationPausePermission(...)`](#checkusagestatsnotificationpausepermission)
* [`checkManageAppPermissionsPermission(...)`](#checkmanageapppermissionspermission)
* [`checkNotificationPermission()`](#checknotificationpermission)
* [`openNotificationSettings()`](#opennotificationsettings)
* [`acquireWakeLock()`](#acquirewakelock)
* [`releaseWakeLock()`](#releasewakelock)
* [`openLocationSettings()`](#openlocationsettings)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### addListener(string, ...)

```typescript
addListener(eventName: string, listenerFunc: () => void) => void
```

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>string</code>        |
| **`listenerFunc`** | <code>() =&gt; void</code> |

--------------------


### showNotificationOnAppClose(...)

```typescript
showNotificationOnAppClose(options: GpsOptions) => Promise<GpsOptions>
```

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#gpsoptions">GpsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#gpsoptions">GpsOptions</a>&gt;</code>

--------------------


### stopNotificationService()

```typescript
stopNotificationService() => Promise<void>
```

--------------------


### checkPermissionsService(...)

```typescript
checkPermissionsService(options: { value: boolean; }) => Promise<{ value: boolean; }>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ value: boolean; }</code> |

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### ignoringBatteryOptimizationsService()

```typescript
ignoringBatteryOptimizationsService() => Promise<void>
```

--------------------


### requestBatteryOptimizations()

```typescript
requestBatteryOptimizations() => Promise<void>
```

--------------------


### requestNotificationPermission()

```typescript
requestNotificationPermission() => Promise<void>
```

--------------------


### checkUsageStatsNotificationPausePermission(...)

```typescript
checkUsageStatsNotificationPausePermission(options: { value: boolean; }) => Promise<{ value: boolean; }>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ value: boolean; }</code> |

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### checkManageAppPermissionsPermission(...)

```typescript
checkManageAppPermissionsPermission(options: { value: boolean; }) => Promise<{ value: boolean; }>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ value: boolean; }</code> |

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### checkNotificationPermission()

```typescript
checkNotificationPermission() => Promise<void>
```

--------------------


### openNotificationSettings()

```typescript
openNotificationSettings() => Promise<void>
```

--------------------


### acquireWakeLock()

```typescript
acquireWakeLock() => Promise<void>
```

--------------------


### releaseWakeLock()

```typescript
releaseWakeLock() => Promise<void>
```

--------------------


### openLocationSettings()

```typescript
openLocationSettings() => Promise<void>
```

--------------------


### Interfaces


#### GpsOptions

| Prop                  | Type                                      |
| --------------------- | ----------------------------------------- |
| **`url`**             | <code><a href="#string">String</a></code> |
| **`id1`**             | <code><a href="#string">String</a></code> |
| **`id2`**             | <code><a href="#string">String</a></code> |
| **`id3`**             | <code><a href="#string">String</a></code> |
| **`id4`**             | <code><a href="#string">String</a></code> |
| **`title`**           | <code><a href="#string">String</a></code> |
| **`timerGps`**        | <code><a href="#number">Number</a></code> |
| **`coordinatesShow`** | <code>boolean</code>                      |
| **`timeShow`**        | <code>boolean</code>                      |


#### String

Allows manipulation and formatting of text strings and determination and location of substrings within strings.

| Prop         | Type                | Description                                                  |
| ------------ | ------------------- | ------------------------------------------------------------ |
| **`length`** | <code>number</code> | Returns the length of a <a href="#string">String</a> object. |

| Method                | Signature                                                                                                                      | Description                                                                                                                                   |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------- |
| **toString**          | () =&gt; string                                                                                                                | Returns a string representation of a string.                                                                                                  |
| **charAt**            | (pos: number) =&gt; string                                                                                                     | Returns the character at the specified index.                                                                                                 |
| **charCodeAt**        | (index: number) =&gt; number                                                                                                   | Returns the Unicode value of the character at the specified location.                                                                         |
| **concat**            | (...strings: string[]) =&gt; string                                                                                            | Returns a string that contains the concatenation of two or more strings.                                                                      |
| **indexOf**           | (searchString: string, position?: number \| undefined) =&gt; number                                                            | Returns the position of the first occurrence of a substring.                                                                                  |
| **lastIndexOf**       | (searchString: string, position?: number \| undefined) =&gt; number                                                            | Returns the last occurrence of a substring in the string.                                                                                     |
| **localeCompare**     | (that: string) =&gt; number                                                                                                    | Determines whether two strings are equivalent in the current locale.                                                                          |
| **match**             | (regexp: string \| <a href="#regexp">RegExp</a>) =&gt; <a href="#regexpmatcharray">RegExpMatchArray</a> \| null                | Matches a string with a regular expression, and returns an array containing the results of that search.                                       |
| **replace**           | (searchValue: string \| <a href="#regexp">RegExp</a>, replaceValue: string) =&gt; string                                       | Replaces text in a string, using a regular expression or search string.                                                                       |
| **replace**           | (searchValue: string \| <a href="#regexp">RegExp</a>, replacer: (substring: string, ...args: any[]) =&gt; string) =&gt; string | Replaces text in a string, using a regular expression or search string.                                                                       |
| **search**            | (regexp: string \| <a href="#regexp">RegExp</a>) =&gt; number                                                                  | Finds the first substring match in a regular expression search.                                                                               |
| **slice**             | (start?: number \| undefined, end?: number \| undefined) =&gt; string                                                          | Returns a section of a string.                                                                                                                |
| **split**             | (separator: string \| <a href="#regexp">RegExp</a>, limit?: number \| undefined) =&gt; string[]                                | Split a string into substrings using the specified separator and return them as an array.                                                     |
| **substring**         | (start: number, end?: number \| undefined) =&gt; string                                                                        | Returns the substring at the specified location within a <a href="#string">String</a> object.                                                 |
| **toLowerCase**       | () =&gt; string                                                                                                                | Converts all the alphabetic characters in a string to lowercase.                                                                              |
| **toLocaleLowerCase** | (locales?: string \| string[] \| undefined) =&gt; string                                                                       | Converts all alphabetic characters to lowercase, taking into account the host environment's current locale.                                   |
| **toUpperCase**       | () =&gt; string                                                                                                                | Converts all the alphabetic characters in a string to uppercase.                                                                              |
| **toLocaleUpperCase** | (locales?: string \| string[] \| undefined) =&gt; string                                                                       | Returns a string where all alphabetic characters have been converted to uppercase, taking into account the host environment's current locale. |
| **trim**              | () =&gt; string                                                                                                                | Removes the leading and trailing white space and line terminator characters from a string.                                                    |
| **substr**            | (from: number, length?: number \| undefined) =&gt; string                                                                      | Gets a substring beginning at the specified location and having the specified length.                                                         |
| **valueOf**           | () =&gt; string                                                                                                                | Returns the primitive value of the specified object.                                                                                          |


#### RegExpMatchArray

| Prop        | Type                |
| ----------- | ------------------- |
| **`index`** | <code>number</code> |
| **`input`** | <code>string</code> |


#### RegExp

| Prop             | Type                 | Description                                                                                                                                                          |
| ---------------- | -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`source`**     | <code>string</code>  | Returns a copy of the text of the regular expression pattern. Read-only. The regExp argument is a Regular expression object. It can be a variable name or a literal. |
| **`global`**     | <code>boolean</code> | Returns a Boolean value indicating the state of the global flag (g) used with a regular expression. Default is false. Read-only.                                     |
| **`ignoreCase`** | <code>boolean</code> | Returns a Boolean value indicating the state of the ignoreCase flag (i) used with a regular expression. Default is false. Read-only.                                 |
| **`multiline`**  | <code>boolean</code> | Returns a Boolean value indicating the state of the multiline flag (m) used with a regular expression. Default is false. Read-only.                                  |
| **`lastIndex`**  | <code>number</code>  |                                                                                                                                                                      |

| Method      | Signature                                                                     | Description                                                                                                                   |
| ----------- | ----------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| **exec**    | (string: string) =&gt; <a href="#regexpexecarray">RegExpExecArray</a> \| null | Executes a search on a string using a regular expression pattern, and returns an array containing the results of that search. |
| **test**    | (string: string) =&gt; boolean                                                | Returns a Boolean value that indicates whether or not a pattern exists in a searched string.                                  |
| **compile** | () =&gt; this                                                                 |                                                                                                                               |


#### RegExpExecArray

| Prop        | Type                |
| ----------- | ------------------- |
| **`index`** | <code>number</code> |
| **`input`** | <code>string</code> |


#### Number

An object that represents a number of any kind. All JavaScript numbers are 64-bit floating-point numbers.

| Method            | Signature                                           | Description                                                                                                                       |
| ----------------- | --------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| **toString**      | (radix?: number \| undefined) =&gt; string          | Returns a string representation of an object.                                                                                     |
| **toFixed**       | (fractionDigits?: number \| undefined) =&gt; string | Returns a string representing a number in fixed-point notation.                                                                   |
| **toExponential** | (fractionDigits?: number \| undefined) =&gt; string | Returns a string containing a number represented in exponential notation.                                                         |
| **toPrecision**   | (precision?: number \| undefined) =&gt; string      | Returns a string containing a number represented either in exponential or fixed-point notation with a specified number of digits. |
| **valueOf**       | () =&gt; number                                     | Returns the primitive value of the specified object.                                                                              |

</docgen-api>
